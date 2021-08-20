package com.safedog.common.autoconfig.swagger;

import com.google.common.base.Predicate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import static springfox.documentation.builders.RequestHandlerSelectors.withClassAnnotation;
import static springfox.documentation.builders.RequestHandlerSelectors.withMethodAnnotation;

/**
 * @author wuxin
 */
@RequiredArgsConstructor
@ConditionalOnClass({ApiInfo.class, Docket.class, com.google.common.base.Predicate.class})
@EnableConfigurationProperties(SwaggerApiDescriptionProperties.class)
public class SwaggerApiDescriptionAutoConfiguration {

    private final SwaggerApiDescriptionProperties swaggerApiDescriptionProperties;

    @Bean
    @ConditionalOnMissingBean
    public Docket apiDescription() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(apiPredicate())
                .build();
    }

    @Bean
    @ConditionalOnProperty(prefix = "swagger.api-description", name = "enable", havingValue = "true", matchIfMissing = true)
    public FilterRegistrationBean<CorsFilter> corsFilterRegistrationBean() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/v2/api-docs", config);
        FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>();
        CorsFilter filter = new CorsFilter(source);
        registrationBean.setFilter(filter);
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registrationBean;
    }

    private Predicate<RequestHandler> apiPredicate() {
        if (swaggerApiDescriptionProperties.isExposureAll()) {
            return e -> true;
        }
        Predicate<RequestHandler> apiAnnotationPredicate = withClassAnnotation(Api.class);
        Predicate<RequestHandler> apiOperationAnnotationPredicate = withMethodAnnotation(ApiOperation.class);
        return e -> apiAnnotationPredicate.apply(e) && apiOperationAnnotationPredicate.apply(e);
    }

    private ApiInfo apiInfo() {
        SwaggerApiDescriptionProperties.Author author = swaggerApiDescriptionProperties.getAuthor();
        Contact contact = null;
        if (author != null) {
            contact = new Contact(author.getName(), null, author.getEmail());
        }
        return new ApiInfoBuilder()
                .title(swaggerApiDescriptionProperties.getTitle())
                .description(swaggerApiDescriptionProperties.getDescription())
                .version(swaggerApiDescriptionProperties.getVersion())
                .contact(contact)
                .build();
    }

}
