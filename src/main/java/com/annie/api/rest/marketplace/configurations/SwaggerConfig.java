package com.annie.api.rest.marketplace.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET,
                        new ArrayList<>(Arrays.asList(
                                new ResponseMessageBuilder()
                                        .code(500)
                                        .message("Internal Server Error")
                                        .responseModel(new ModelRef("Error"))
                                        .build(),
                                new ResponseMessageBuilder()
                                        .code(404)
                                        .message("Resources Not Found")
                                        .responseModel(new ModelRef("Error"))
                                        .build())
                        )
                )
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.annie.api.rest.marketplace.controllers"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Marketplace Api",
                "Market Place is an application where people can register, upload their products and sell them.",
                "1.0",
                "Terms of Service",
                new Contact("Annie Pinter-Nguyen", "https://github.com/nguyenannie",
                        "annie.pinternguyen@gmail.com"),
                "License of API", "API license URL", Collections.emptyList()
        );
    }
}
