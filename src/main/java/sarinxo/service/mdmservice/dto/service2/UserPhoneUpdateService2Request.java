package sarinxo.service.mdmservice.dto.service2;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserPhoneUpdateService2Request {

    UUID id;
    String systemId;
    List<UserPhoneUpdateEvent> events;

}
