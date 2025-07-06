package com.example.ticket.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API Dịch vụ ABC",
                version = "1.0.0",
                description = "Tài liệu API cho các dịch vụ của hệ thống ABC.",
                contact = @Contact(
                        name = "Hỗ trợ kỹ thuật",
                        email = "support@abc.com"
                ),
                license = @License(
                        name = "Giấy phép Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0"
                )
        )
)
public class OpenApiConfig {
    // Class này có thể để trống
}
