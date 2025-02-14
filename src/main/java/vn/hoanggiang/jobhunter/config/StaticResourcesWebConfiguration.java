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
        // Any url starting with /storage/** will be searched in the basePath directory
        // Ex: /storage/abc.jpg will find abc.jpg in the basePath directory
        registry.addResourceHandler("/storage/**")
                .addResourceLocations(basePath);
    }
}
