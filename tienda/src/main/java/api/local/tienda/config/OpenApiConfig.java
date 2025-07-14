package api.local.tienda.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("tienda-api")
                .pathsToMatch("/**")   // todos los endpoints
                .build();
    }

    @Bean
    public io.swagger.v3.oas.models.OpenAPI customOpenAPI() {
        return new io.swagger.v3.oas.models.OpenAPI()
                .info(new Info()
                        .title("Tienda – API REST")
                        .version("1.0")
                        .description("API de ventas, productos y clientes de una tienda")
                        .license(new License().name("MIT").url("https://opensource.org/licenses/MIT")));
    }
}
