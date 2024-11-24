package vn.hoanggiang.jobhunter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourcesWebConfiguration
        implements WebMvcConfigurer {

    @Value("${hoanggiang.upload-file.base-uri}")
    private String basePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //url nào bắt đầu /storage/** sẽ được tìm trong thư mục basePath
        // VD: /storage/abc.jpg sẽ tìm trong thư mục basePath/abc.jpg
        registry.addResourceHandler("/storage/**")
                .addResourceLocations(basePath);
    }
}

