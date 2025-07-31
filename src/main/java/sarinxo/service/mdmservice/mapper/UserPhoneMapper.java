package sarinxo.service.mdmservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import sarinxo.service.mdmservice.dto.kafka.UserEventKafkaDto;
import sarinxo.service.mdmservice.dto.service1.UserPhoneUpdateService1RequestBody;
import sarinxo.service.mdmservice.dto.service2.UserPhoneUpdateEvent;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserPhoneMapper {

    UserPhoneUpdateService1RequestBody eventToService1RequestBody(UserEventKafkaDto dto);

    @Mapping(source = "type", target = "eventType", qualifiedByName = "mapEventType")
    UserPhoneUpdateEvent eventToService2RequestBody(UserEventKafkaDto event);

    @Named("mapEventType")
    static String mapEventType(String value) {
        if ("USER_PHONE_CHANGE".equals(value)) {
            return "change_phone";
        }
        return value;
    }

}
