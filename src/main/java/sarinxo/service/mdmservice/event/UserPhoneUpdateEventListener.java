package sarinxo.service.mdmservice.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
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
public class UserPhoneUpdateEventListener {

    private final UserPhoneMapper mapper;
    private final AppProperties appProperties;
    private final Service1FeignClient service1FeignClient;
    private final Service2FeignClient service2FeignClient;

    @EventListener
    public void sendInService1(UserEventKafkaDto event) {
        log.debug("Event Listener: send message in service1: {}", event);
        RequestMeta meta = RequestMeta.builder()
                .sender("SarinXo")
                .systemId(appProperties.appName())
                .build();
        UserPhoneUpdateService1RequestBody body = mapper.eventToService1RequestBody(event);
        UserPhoneUpdateService1Request request = new UserPhoneUpdateService1Request(meta, body);

        service1FeignClient.updatePhone(request);
    }

    @EventListener
    public void sendInService2(UserEventKafkaDto event) {
        log.debug("Event Listener: send message in service2: {}", event);
        UserPhoneUpdateEvent body = mapper.eventToService2RequestBody(event);
        UserPhoneUpdateService2Request request = UserPhoneUpdateService2Request.builder()
                .id(UUID.randomUUID())
                .systemId(appProperties.appName())
                .events(List.of(body))
                .build();

        service2FeignClient.updatePhone(request);
    }

}
