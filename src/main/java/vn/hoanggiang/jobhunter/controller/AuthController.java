package vn.hoanggiang.jobhunter.controller;

import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vn.hoanggiang.jobhunter.domain.User;
import vn.hoanggiang.jobhunter.domain.request.ReqLoginDTO;
import vn.hoanggiang.jobhunter.domain.response.ResLoginDTO;
import vn.hoanggiang.jobhunter.domain.response.user.ResCreateUserDTO;
import vn.hoanggiang.jobhunter.service.UserService;
import vn.hoanggiang.jobhunter.util.SecurityUtil;
import vn.hoanggiang.jobhunter.util.annotation.ApiMessage;
import vn.hoanggiang.jobhunter.util.error.IdInvalidException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final SecurityUtil securityUtil;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Value("${hoanggiang.jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenExpiration;

    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder,
            SecurityUtil securityUtil, UserService userService, PasswordEncoder passwordEncoder) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securityUtil = securityUtil;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<ResLoginDTO> login(@Valid @RequestBody ReqLoginDTO loginDTO) {
        // tạo một token xác thực với thông tin username và password từ loginDTO
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(), loginDTO.getPassword());

        // authenticationManagerBuilder sẽ gọi đến UserDetailsService để tìm kiếm thông
        // tin người dùng từ cơ sở dữ liệu (loadUserByUsername)
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // lưu trữ thông tin xác thực vào SecurityContext để sử dụng cho các request
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // instead getting from principal, we get data from query in order to return FE
        // response of login
        ResLoginDTO res = new ResLoginDTO();

        User currentUserDB = this.userService.handleGetUserByUsername(loginDTO.getUsername());

        if (currentUserDB != null) {
            ResLoginDTO.UserLogin userLogin = new ResLoginDTO.UserLogin(
                    currentUserDB.getId(), currentUserDB.getEmail(), currentUserDB.getName(), currentUserDB.getRole());
            res.setUser(userLogin);
        }

        // create a token
        String access_token = this.securityUtil.createAccessToken(authentication.getName(), res);

        res.setAccessToken(access_token);

        // create refresh token
        String refresh_token = this.securityUtil.createRefreshToken(loginDTO.getUsername(), res);

        // save user's refresh token into DB
        this.userService.updateUserToken(refresh_token, loginDTO.getUsername());

        // set cookies
        // save refresh toke into cookie
        ResponseCookie resCookies = ResponseCookie
                .from("refresh_token", refresh_token)
                // cookie chỉ được truy cập bởi server, tránh lộ thông tin qua JavaScript
                .httpOnly(true)
                // chỉ gửi cookie qua HTTPS
                .secure(true)
                // url set cookie
                .path("/")
                .maxAge(refreshTokenExpiration)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, resCookies.toString())
                .body(res);
    }

    // get current user's account
    @GetMapping("/account")
    @ApiMessage("fetch account")
    public ResponseEntity<ResLoginDTO.UserGetAccount> getAccount() {
        String email = SecurityUtil.getCurrentUserLogin().orElse("");

        User currentUserDB = this.userService.handleGetUserByUsername(email);
        ResLoginDTO.UserLogin userLogin = new ResLoginDTO.UserLogin();
        ResLoginDTO.UserGetAccount userGetAccount = new ResLoginDTO.UserGetAccount();

        if (currentUserDB != null) {
            userLogin.setId(currentUserDB.getId());
            userLogin.setEmail(currentUserDB.getEmail());
            userLogin.setName(currentUserDB.getName());
            userLogin.setRole(currentUserDB.getRole());

            userGetAccount.setUser(userLogin);
        }

        return ResponseEntity.ok().body(userGetAccount);
    }

    @GetMapping("/refresh")
    @ApiMessage("get User by refresh token")
    public ResponseEntity<ResLoginDTO> getRefreshToken(
            @CookieValue(name = "refresh_token", defaultValue = "abc") String refresh_token) throws IdInvalidException {
        if (refresh_token.equals("abc")) {
            throw new IdInvalidException("Bạn không có refresh token ở cookie");
        }

        // check valid
        Jwt decodedToken = this.securityUtil.checkValidRefreshToken(refresh_token);
        String email = decodedToken.getSubject();
        // check user by token + email
        User currentUser = this.userService.getUserByRefreshTokenAndEmail(refresh_token, email);
        if (currentUser == null) {
            throw new IdInvalidException("Refresh Token không hợp lệ");
        }

        ResLoginDTO res = new ResLoginDTO();

        User currentUserDB = this.userService.handleGetUserByUsername(email);

        if (currentUserDB != null) {
            ResLoginDTO.UserLogin userLogin = new ResLoginDTO.UserLogin(
                    currentUserDB.getId(), currentUserDB.getEmail(), currentUserDB.getName(), currentUserDB.getRole());
            res.setUser(userLogin);
        }
        // create a token
        String access_token = this.securityUtil.createAccessToken(email, res);

        res.setAccessToken(access_token);

        // create refresh token
        String new_refresh_token = this.securityUtil.createRefreshToken(email, res);

        // update user's token into DB
        this.userService.updateUserToken(refresh_token, email);

        // set cookies
        // tạo Refresh Token và lưu vào cookie
        ResponseCookie resCookies = ResponseCookie
                .from("refresh_token", new_refresh_token)
                // cookie chỉ được truy cập bởi server, tránh lộ thông tin qua JavaScript
                .httpOnly(true)
                // chỉ gửi cookie qua HTTPS
                .secure(true)
                // url set cookie
                .path("/")
                .maxAge(refreshTokenExpiration)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, resCookies.toString())
                .body(res);
    }

    @PostMapping("/logout")
    @ApiMessage("logout User")
    public ResponseEntity<Void> logout() throws IdInvalidException {
        String email = SecurityUtil.getCurrentUserLogin().orElse(null);

        if (email.equals("")) {
            throw new IdInvalidException("Access Token không hợp lệ");
        }

        // update refresh token = null
        this.userService.updateUserToken(null, email);

        // remove refresh token cookie
        ResponseCookie deleteSpringCookie = ResponseCookie
                .from("refresh_token", null)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, deleteSpringCookie.toString())
                .body(null);
    }

    @PostMapping("/register")
    @ApiMessage("register a new user")
    public ResponseEntity<ResCreateUserDTO> register(@Valid @RequestBody User reqUser) throws IdInvalidException {
        // check email exist or not
        boolean isEmailExist = this.userService.isEmailExist(reqUser.getEmail());
        if (isEmailExist) {
            throw new IdInvalidException(
                    "Email " + reqUser.getEmail() + "đã tồn tại, vui lòng sử dụng email khác.");
        }

        String hashPassword = this.passwordEncoder.encode(reqUser.getPassword());
        reqUser.setPassword(hashPassword);

        User user = this.userService.handleCreateUser(reqUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.convertToResCreateUserDTO(user));
    }
}
