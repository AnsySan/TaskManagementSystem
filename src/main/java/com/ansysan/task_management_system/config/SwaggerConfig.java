package com.ansysan.task_management_system.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI usersOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Task Management System API")
                        .description("Description")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Evgenij Pashkevich")
                                .email("epaskevic4@gmail.com")
                                .url("https://github.com/AnsySan")));
    }
}
