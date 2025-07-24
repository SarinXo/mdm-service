package sarinxo.service.mdmservice.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import sarinxo.service.mdmservice.config.property.KafkaTopicProperties;
import sarinxo.service.mdmservice.dto.kafka.UserEventKafkaDto;

@Slf4j
@Component
public class KafkaConsumer {

    private final KafkaTopicProperties kafkaTopicProperties;

    public KafkaConsumer(KafkaTopicProperties kafkaTopicProperties) {
        this.kafkaTopicProperties = kafkaTopicProperties;
    }

    @KafkaListener(
            topics = "${mdm-service.kafka.user-event.topic-name}",
            containerFactory = "userEventListenerContainerFactory"
    )
    public void consume(UserEventKafkaDto dto) {
        log.info("Consumer received: {}", dto);
        //логики пока нет
    }

    @KafkaListener(
            topics = "${mdm-service.kafka.user-event.topic-name}${mdm-service.kafka.dlt-topic-suffix}"
    )
    public void consumeDlt(String dto) {
        log.info("Consumer DLT received: {}", dto);
        //логики пока нет
    }
}
