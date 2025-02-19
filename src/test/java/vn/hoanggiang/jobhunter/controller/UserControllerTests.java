package vn.hoanggiang.jobhunter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import vn.hoanggiang.jobhunter.domain.Company;
import vn.hoanggiang.jobhunter.domain.Role;
import vn.hoanggiang.jobhunter.domain.User;
import vn.hoanggiang.jobhunter.domain.response.ResUserDTO;
import vn.hoanggiang.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoanggiang.jobhunter.service.UserService;
import vn.hoanggiang.jobhunter.util.constant.GenderEnum;

import java.util.Arrays;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserService userService;

    private User request;

    private User userResponse;


    private ResUserDTO resUserDTO;


    @BeforeEach
    void initData(){
        ResUserDTO.CompanyUser companyUser;
        Company company = new Company();
        company.setId(4L);

        Role role = new Role();
        role.setId(1L);

        companyUser = new ResUserDTO.CompanyUser();
        companyUser.setId(4L);
        companyUser.setName("FPT company");

        request = User.builder()
                .name("Hoang Giang")
                .email("hg124@gmail.com")
                .gender(GenderEnum.FEMALE)
                .password("123456")
                .address("FEMALE")
                .age(20)
                .company(company)
                .role(role)
                .build();

        String encodedPassword = passwordEncoder.encode("123456");
        userResponse = User.builder()
                .id(9)
                .name("Hoang Giang")
                .email("hg124@gmail.com")
                .gender(GenderEnum.FEMALE)
                .password(encodedPassword)
                .address("FEMALE")
                .age(20)
                .company(company)
                .build();

        resUserDTO = ResUserDTO.builder()
                .id(userResponse.getId())
                .name(userResponse.getName())
                .email(userResponse.getEmail())
                .age(userResponse.getAge())
                .gender(userResponse.getGender())
                .address(userResponse.getAddress())
                .company(companyUser)
                .build();
    }

    // ------------------ TEST CHO CREATE USER ------------------

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void createUser_validRequest_success() throws Exception {
        // GIVEN
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(request);

        Mockito.when(userService.isEmailExist(request.getEmail())).thenReturn(false);
        Mockito.when(userService.handleCreateUser(ArgumentMatchers.any()))
                .thenReturn(userResponse);
        Mockito.when(userService.convertToResUserDTO(userResponse)).thenReturn(resUserDTO);

        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("statusCode").value(201))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("create a new user"))
                .andExpect(MockMvcResultMatchers.jsonPath("data.id").value(9));

        // Check password
        boolean isPasswordMatching = passwordEncoder.matches("123456", userResponse.getPassword());
        Assertions.assertTrue(isPasswordMatching, "Password should match the hashed value");
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void createUser_invalidEmail_fail() throws Exception {
        // GIVEN
        request.setEmail("");
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(request);

        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("statusCode")
                        .value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("message")
                        .value("email cannot be blank")
                );
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void createUser_emailExists_fail() throws Exception {
        // GIVEN
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(request);

        Mockito.when(userService.isEmailExist(request.getEmail())).thenReturn(true);

        // WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("statusCode").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("message")
                        .value("Email " + request.getEmail() + " already exists, please use another email."))
                .andExpect(MockMvcResultMatchers.jsonPath("error").value("Exception occurs..."));
    }

    // ------------------ TEST CHO UPDATE USER ------------------

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void updateUser_validRequest_success() throws Exception {
        request.setId(userResponse.getId());

        request.setName("Giang Hoang");
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(request);

        // Simulate the result returned after update
        User updatedUser = User.builder()
                .id(userResponse.getId())
                .name("Giang Hoang")
                .email(userResponse.getEmail())
                .gender(userResponse.getGender())
                .password(userResponse.getPassword())
                .address(userResponse.getAddress())
                .age(userResponse.getAge())
                .company(userResponse.getCompany())
                .role(userResponse.getRole())
                .build();

        resUserDTO.setName("Giang Hoang");


        Mockito.when(userService.handleUpdateUser(ArgumentMatchers.any(User.class))).thenReturn(updatedUser);
        Mockito.when(userService.convertToResUserDTO(updatedUser)).thenReturn(resUserDTO);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("data.id").value(userResponse.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("data.name").value("Giang Hoang"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void updateUser_invalidId_fail() throws Exception {
        request.setId(999L);
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(request);

        // Mock handleUpdateUser returns null
        Mockito.when(userService.handleUpdateUser(ArgumentMatchers.any(User.class))).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("message")
                        .value("User with id = " + request.getId() + " does not exist"));
    }

    // ------------------ TEST CHO DELETE USER ------------------

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deleteUser_validId_success() throws Exception {
        long id = userResponse.getId();
        Mockito.when(userService.fetchUserById(id)).thenReturn(userResponse);

        // handleDeleteUser returns nothing so use doNothing()
        Mockito.doNothing().when(userService).handleDeleteUser(id);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/users/" + id))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deleteUser_invalidId_fail() throws Exception {
        long id = 999L;
        Mockito.when(userService.fetchUserById(id)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/users/" + id))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("message")
                        .value("User with id = " + id + " does not exist"));
    }

    // ------------------ TEST CHO GET USER BY ID ------------------

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getUserById_validId_success() throws Exception {
        long id = userResponse.getId();
        Mockito.when(userService.fetchUserById(id)).thenReturn(userResponse);


        Mockito.when(userService.convertToResUserDTO(userResponse)).thenReturn(resUserDTO);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/users/" + id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("data.id").value(userResponse.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("data.email").value(userResponse.getEmail()));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getUserById_invalidId_fail() throws Exception {
        long id = 999L;
        Mockito.when(userService.fetchUserById(id)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/users/" + id))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("message")
                        .value("User with id = " + id + " does not exist"));
    }

    // ------------------ TEST CHO GET ALL USERS ------------------

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void getAllUser_success() throws Exception {
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();
        meta.setPage(0);
        meta.setPageSize(10);
        meta.setPages(1);
        meta.setTotal(1L);

        ResultPaginationDTO resultPaginationDTO = new ResultPaginationDTO();
        resultPaginationDTO.setMeta(meta);
        resultPaginationDTO.setResult(Arrays.asList(userResponse));

        // Simulate service that returns pagination results
        Mockito.when(userService.fetchAllUser(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(resultPaginationDTO);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/users")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("data.meta.page").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("data.meta.pageSize").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("data.meta.pages").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("data.meta.total").value(1));
    }
}
