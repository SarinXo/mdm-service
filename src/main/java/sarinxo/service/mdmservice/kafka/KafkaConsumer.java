package sarinxo.service.mdmservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import sarinxo.service.mdmservice.dto.kafka.UserEventKafkaDto;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private final ApplicationEventPublisher eventPublisher;

    @KafkaListener(
            topics = "${mdm-service.kafka.user-event.topic-name}",
            containerFactory = "userEventListenerContainerFactory"
    )
    public void consume(UserEventKafkaDto dto) {
        log.info("Consumer received: {}", dto);
        eventPublisher.publishEvent(dto);
    }

    @KafkaListener(
            topics = "${mdm-service.kafka.user-event.topic-name}${mdm-service.kafka.dlt-topic-suffix}"
    )
    public void consumeDlt(String dto) {
        log.info("Consumer DLT received: {}", dto);
        //логики пока нет
    }

}
