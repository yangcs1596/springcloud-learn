package com.safedog.common.autoconfigure.swagger;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wuxin
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConfigurationProperties(prefix = "swagger.api-description")
public class SwaggerApiDescriptionProperties {

    String title = "Safedog CloudNet Documentation";
    String description = "Safedog CloudNet Documentation";
    String version = "1.0";
    Author author;
    boolean exposureAll = false;
    boolean cors = true;

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Author {
        String name;
        String email;
    }

}
