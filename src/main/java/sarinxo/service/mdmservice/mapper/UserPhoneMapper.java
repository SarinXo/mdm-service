package sarinxo.service.mdmservice.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import sarinxo.service.mdmservice.dto.kafka.UserEventKafkaDto;
import sarinxo.service.mdmservice.dto.service1.RequestMeta;
import sarinxo.service.mdmservice.dto.service1.UserPhoneUpdateService1Request;
import sarinxo.service.mdmservice.dto.service2.UserPhoneUpdateService2Request;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UserPhoneMapper {

    @Mapping(source = "id", target = "body.id")
    @Mapping(source = "guid", target = "body.guid")
    @Mapping(source = "phone", target = "body.phone")
    UserPhoneUpdateService1Request eventToService1Request(UserEventKafkaDto dto);

    @AfterMapping
    default void initMeta(@MappingTarget UserPhoneUpdateService1Request request) {
        request.setMeta(new RequestMeta());
    }

    UserPhoneUpdateService2Request eventToService2Request(UserEventKafkaDto event);

}
