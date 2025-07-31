package sarinxo.service.mdmservice.config.property;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties("mdm-service.meta")
public record AppProperties(
        /*
         * Название приложения
         */
        @NotEmpty(message = "Field 'appName' can't be empty")
        String appName
) {
}
