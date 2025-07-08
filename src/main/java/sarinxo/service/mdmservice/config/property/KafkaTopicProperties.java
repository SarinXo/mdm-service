package sarinxo.service.mdmservice.config.property;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties("mdm-service.kafka")
public record KafkaTopicProperties(
        /*
         * Суффикс для DLQ топиков
         */
         @NotEmpty(message = "Field 'dltTopicSuffix' can't be empty")
         String dltTopicSuffix,
        /*
         * Топик, в котором передаем UserEventDto
         */
         @Valid
         UserEvent userEvent
) {

    public record UserEvent(
            @NotNull(message = "Field 'topicName' can't be null")
            String topicName
    ) {

    }

}
