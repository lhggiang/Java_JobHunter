package vn.hoanggiang.jobhunter.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import vn.hoanggiang.jobhunter.domain.Permission;
import vn.hoanggiang.jobhunter.domain.Role;
import vn.hoanggiang.jobhunter.domain.User;
import vn.hoanggiang.jobhunter.service.UserService;
import vn.hoanggiang.jobhunter.util.SecurityUtil;
import vn.hoanggiang.jobhunter.util.error.IdInvalidException;
import vn.hoanggiang.jobhunter.util.error.PermissionException;

@Component
public class PermissionInterceptor implements HandlerInterceptor {

  @Autowired
  private UserService userService;

  @Override
  @Transactional
  public boolean preHandle(
      HttpServletRequest request,
      HttpServletResponse response, Object handler)
      throws Exception {

    String path = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
    // String requestURI = request.getRequestURI();
    String httpMethod = request.getMethod();
    // System.out.println(">>> RUN preHandle");
    // System.out.println(">>> path= " + path);
    // System.out.println(">>> httpMethod= " + httpMethod);
    // System.out.println(">>> requestURI= " + requestURI);

    // check permission
    String email = SecurityUtil.getCurrentUserLogin().isPresent()
        ? SecurityUtil.getCurrentUserLogin().get()
        : "";
    if (!email.isEmpty()) {
      User user = this.userService.handleGetUserByUsername(email);
      if (user != null) {
        Role role = user.getRole();
        if (role != null) {
          List<Permission> permissions = role.getPermissions();
          boolean isAllow = permissions.stream().anyMatch(item -> item.getApiPath().equals(path)
              && item.getMethod().equals(httpMethod));

          if (!isAllow) {
            throw new PermissionException("Bạn không có quyền truy cập endpoint này.");
          }
        } else {
          throw new PermissionException("Bạn không có quyền truy cập endpoint này.");
        }
      }
    }
    return true;
  }
}