/*
 * SteVe - SteckdosenVerwaltung - https://github.com/steve-community/steve
 * Copyright (C) 2013-2023 SteVe Community Team
 * All Rights Reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package de.rwth.idsg.steve.config;

import de.rwth.idsg.steve.SteveConfiguration;
import de.rwth.idsg.steve.SteveProdCondition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;
import java.util.List;

import static de.rwth.idsg.steve.SteveConfiguration.CONFIG;
import static springfox.documentation.builders.RequestHandlerSelectors.withClassAnnotation;

/**
 * @author Sevket Goekay <sevketgokay@gmail.com>
 * @since 15.09.2022
 */
@Configuration
@EnableOpenApi
@Conditional(SteveProdCondition.class)
public class ApiDocsConfiguration {

  static {
    // Set the path with prefix /manager to protect the documentation behind regular sign-in
    // Default is just /v3/api-docs
    System.setProperty("springfox.documentation.open-api.v3.path", "/manager/v3/api-docs");
  }

  @Bean
  public Docket apiDocs() {
    String title = "SteVe REST API Documentation";

    var apiInfo = new ApiInfoBuilder()
        .title(title)
        .description(title)
        .license("GPL-3.0")
        .licenseUrl("https://github.com/steve-community/steve/blob/master/LICENSE.txt")
        .version(SteveConfiguration.CONFIG.getSteveVersion())
        .build();

    return new Docket(DocumentationType.OAS_30)
        .useDefaultResponseMessages(false)
        .apiInfo(apiInfo)
        .select()
        .apis(withClassAnnotation(RestController.class))
        .build()
        .securitySchemes(Collections.singletonList(apiKey()))
        .securityContexts(Collections.singletonList(securityContext()));
  }

  private static ApiKey apiKey() {
    return new ApiKey("apiKey", CONFIG.getWebApi().getHeaderKey(), "header");
  }

  private static SecurityContext securityContext() {
    return SecurityContext.builder()
        .securityReferences(defaultAuth())
        .operationSelector(ApiDocsConfiguration::matchesApiPath)
        .build();
  }

  private static boolean matchesApiPath(OperationContext context) {
    return context.requestMappingPattern().matches(CONFIG.getApiMapping() + "/.*");
  }

  private static List<SecurityReference> defaultAuth() {
    AuthorizationScope authorizationScope
        = new AuthorizationScope("global", "accessEverything");
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    return Collections.singletonList(
        new SecurityReference("apiKey", authorizationScopes));
  }
}
