package br.tech.oe.plan.config;

import br.tech.oe.plan.dto.APIInfoDTO;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    private final APIInfoDTO api;

    @Autowired
    public SpringDocConfig(APIInfoDTO api) {
        this.api = api;
    }

    @Bean
    public OpenAPI springDocsOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(api.getTitle())
                        .description(api.getDescription())
                        .version(api.getVersion())
                        .license(new License()
                                .name(api.getLicense())
                                .url(api.getLicenseUrl())
                        )
                )
                .addServersItem(new Server().url(api.getUrl()).description(api.getTitle() + " Project"));
    }
}