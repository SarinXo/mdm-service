package sarinxo.service.mdmservice.dto.service2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserPhoneUpdateService2Request {

    String id;
    String systemId;
    List<UserPhoneUpdateEvent> events;

}
