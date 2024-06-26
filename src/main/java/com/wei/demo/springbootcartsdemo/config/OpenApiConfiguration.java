package com.wei.demo.springbootcartsdemo.config;


import com.wei.demo.springbootcartsdemo.error.ApiError;
import com.wei.demo.springbootcartsdemo.error.ApiErrorCode;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.SpringDocUtils;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Configuration
@Slf4j
public class OpenApiConfiguration {
    static {
        SpringDocUtils.getConfig().replaceWithSchema(LocalTime.class, new Schema<LocalTime>()
                .example(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")))
                .type("string"));
    }

    @Value("${openapi.server.url}")
    private String serverUrl;

    @Value("${openapi.server.description}")
    private String serverDesc;

    @Autowired
    private ResourceLoader resourceLoader;


    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(getDefaultApiInfo())
                .servers(Arrays.asList(new Server().url(serverUrl).description(serverDesc)))
                .components(new Components()
                        .addSecuritySchemes("BearerToken", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer").bearerFormat("JWT")))
                .security(Arrays.asList(
                        new SecurityRequirement().addList("BearerToken"))
                );

    }

    private Info getDefaultApiInfo() {
        try (InputStream descIn = resourceLoader.getResource("classpath:default-open-api-desc.md").getInputStream()) {
            String desc = IOUtils.toString(descIn, StandardCharsets.UTF_8);
            return new Info().title("Shopping Carts Demo Open API").description(desc);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private OpenApiCustomiser openAPICustomiser(Info info) {
        ModelConverters modelConverter = ModelConverters.getInstance();
        return openApi -> {
            openApi.setInfo(info);
            modelConverter.readAllAsResolvedSchema(ApiError.class)
                    .referencedSchemas.entrySet().forEach(s -> openApi.getComponents().addSchemas(s.getKey(), s.getValue()));
            modelConverter.readAllAsResolvedSchema(ApiErrorCode.class)
                    .referencedSchemas.entrySet().forEach(s -> openApi.getComponents().addSchemas(s.getKey(), s.getValue()));
        };
    }

    @Bean
    public GroupedOpenApi userGroup() {
        return GroupedOpenApi.builder()
                .group("default")
                .packagesToScan("com.wei.demo.springbootcartsdemo.controller", "com.wei.demo.springbootcartsdemo.model", "com.wei.demo.springbootcartsdemo.error")
                .pathsToMatch("/api/**")
                .addOpenApiCustomiser(openAPICustomiser(getDefaultApiInfo()))
                .build();
    }

    @Bean
    public GroupedOpenApi adminGroup() {
        return GroupedOpenApi.builder()
                .group("admin")
                .packagesToScan("com.wei.demo.springbootcartsdemo.controller", "com.wei.demo.springbootcartsdemo.model", "com.wei.demo.springbootcartsdemo.error")
                .pathsToMatch("/api/**")
                .addOpenApiCustomiser(openAPICustomiser(getDefaultApiInfo()))
                .build();
    }

    @Bean
    public GroupedOpenApi customerGroup() {
        return GroupedOpenApi.builder()
                .group("member")
                .packagesToScan("com.wei.demo.springbootcartsdemo.controller", "com.wei.demo.springbootcartsdemo.model", "com.wei.demo.springbootcartsdemo.error")
                .pathsToMatch("/api/**")
                .addOpenApiCustomiser(openAPICustomiser(getDefaultApiInfo()))
                .build();
    }

}
