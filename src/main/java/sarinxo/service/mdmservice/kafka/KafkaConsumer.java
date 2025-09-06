package sarinxo.service.mdmservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import sarinxo.service.mdmservice.client.Service1FeignClient;
import sarinxo.service.mdmservice.client.Service2FeignClient;
import sarinxo.service.mdmservice.config.property.AppProperties;
import sarinxo.service.mdmservice.dto.kafka.UserEventKafkaDto;
import sarinxo.service.mdmservice.dto.service1.RequestMeta;
import sarinxo.service.mdmservice.dto.service1.UserPhoneUpdateService1Request;
import sarinxo.service.mdmservice.dto.service1.UserPhoneUpdateService1RequestBody;
import sarinxo.service.mdmservice.dto.service2.UserPhoneUpdateEvent;
import sarinxo.service.mdmservice.dto.service2.UserPhoneUpdateService2Request;
import sarinxo.service.mdmservice.mapper.UserPhoneMapper;

import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private final UserPhoneMapper mapper;
    private final AppProperties appProperties;
    private final Service1FeignClient service1FeignClient;
    private final Service2FeignClient service2FeignClient;

    @KafkaListener(
            topics = "${mdm-service.kafka.user-event.topic-name}",
            containerFactory = "userEventListenerContainerFactory"
    )
    public void consume(UserEventKafkaDto dto) {
        log.info("Consumer received: {}", dto);
        try {
            log.debug("Event Listener: send message in service1: {}", dto);
            RequestMeta meta = RequestMeta.builder()
                    .sender("SarinXo")
                    .systemId(appProperties.appName())
                    .build();
            UserPhoneUpdateService1RequestBody body = mapper.eventToService1RequestBody(dto);
            UserPhoneUpdateService1Request request = new UserPhoneUpdateService1Request(meta, body);

            service1FeignClient.updatePhone(request);
        } catch (Exception e) {
            log.error("Error while send message", e);
        }
        try {
            log.debug("Event Listener: send message in service2: {}", dto);
            UserPhoneUpdateEvent body = mapper.eventToService2RequestBody(dto, "change_phone");
            UserPhoneUpdateService2Request request = UserPhoneUpdateService2Request.builder()
                    .id(UUID.randomUUID())
                    .systemId(appProperties.appName())
                    .events(List.of(body))
                    .build();

            service2FeignClient.updatePhone(request);
        } catch (Exception e) {
            log.error("Error while send message", e);
        }
    }

    @KafkaListener(
            topics = "${mdm-service.kafka.user-event.topic-name}${mdm-service.kafka.dlt-topic-suffix}"
    )
    public void consumeDlt(String dto) {
        log.info("Consumer DLT received: {}", dto);
        //логики пока нет
    }

}
