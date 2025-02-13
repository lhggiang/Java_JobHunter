package vn.hoanggiang.jobhunter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class PermissionInterceptorConfiguration implements WebMvcConfigurer {
  @Autowired
  private PermissionInterceptor permissionInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    String[] whiteList = {
        "/", "/api/v1/auth/**", "/storage/**",
        "/api/v1/companies/**", "/api/v1/jobs/**", "/api/v1/skills/**", "/api/v1/files",
        "/api/v1/resumes/**",
        "/api/v1/subscribers/**",
        "/api/v1/statistics/**"
    };
    registry.addInterceptor(permissionInterceptor)
        .excludePathPatterns(whiteList);
  }
}
