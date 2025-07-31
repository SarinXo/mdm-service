package sarinxo.service.mdmservice.kafka;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import sarinxo.service.mdmservice.client.Service1FeignClient;
import sarinxo.service.mdmservice.client.Service2FeignClient;
import sarinxo.service.mdmservice.config.property.AppProperties;
import sarinxo.service.mdmservice.config.property.KafkaTopicProperties;
import sarinxo.service.mdmservice.dto.kafka.UserEventKafkaDto;
import sarinxo.service.mdmservice.dto.service1.RequestMeta;
import sarinxo.service.mdmservice.dto.service1.UserPhoneUpdateService1Request;
import sarinxo.service.mdmservice.dto.service1.UserPhoneUpdateService1RequestBody;
import sarinxo.service.mdmservice.dto.service2.UserPhoneUpdateService2Request;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private final KafkaTopicProperties kafkaTopicProperties;
    private final AppProperties appProperties;
    private final Service1FeignClient service1FeignClient;
    private final Service2FeignClient service2FeignClient;

    @KafkaListener(
            topics = "${mdm-service.kafka.user-event.topic-name}",
            containerFactory = "userEventListenerContainerFactory"
    )
    public void consume(UserEventKafkaDto dto) {
        log.info("Consumer received: {}", dto);
        RequestMeta meta =  RequestMeta.builder()
                .sender("SarinXo")
                .systemId(appProperties.appName())
                .build();
        UserPhoneUpdateService1RequestBody body = UserPhoneUpdateService1RequestBody.builder()
                .id(dto.getId())
                .guid(dto.getGuid())
                .phone(dto.getPhone())
                .build();
        UserPhoneUpdateService1Request userPhoneUpdateService1Request = new UserPhoneUpdateService1Request(meta, body);


        UserPhoneUpdateService2Request userPhoneUpdateService2Request = new UserPhoneUpdateService2Request();
        service1FeignClient.updatePhone();
        service2FeignClient.updatePhone();


    }

    @KafkaListener(
            topics = "${mdm-service.kafka.user-event.topic-name}${mdm-service.kafka.dlt-topic-suffix}"
    )
    public void consumeDlt(String dto) {
        log.info("Consumer DLT received: {}", dto);
        //логики пока нет
    }
}
