package sarinxo.service.mdmservice.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;
import sarinxo.service.mdmservice.config.property.KafkaTopicProperties;
import sarinxo.service.mdmservice.dto.UserEventDto;

@Slf4j
@Component
public class KafkaConsumer {

    private final KafkaTopicProperties kafkaTopicProperties;

    public KafkaConsumer(KafkaTopicProperties kafkaTopicProperties) {
        this.kafkaTopicProperties = kafkaTopicProperties;
    }

    @RetryableTopic(
            attempts = "5",
            backoff = @Backoff(
                    delay = 500,
                    multiplier = 1.25,
                    maxDelay = 3000
            )
    )
    @KafkaListener(
            topics = "${mdm-service.kafka.user-event.topic-name}",
            containerFactory = "userEventListenerContainerFactory"
    )
    public void consume(UserEventDto dto) {
        log.info("Consumer received: {}", dto);
        //логики пока нет
    }

    @KafkaListener(
            topics = "${mdm-service.kafka.user-event.topic-name}.DLT"
    )
    public void consumeDlt(String dto) {
        log.info("Consumer DLT received: {}", dto);
        //логики пока нет
    }
}
