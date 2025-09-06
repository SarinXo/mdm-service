package sarinxo.service.mdmservice.dto.service2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserPhoneUpdateEvent {

    private String eventType;
    private String guid;
    private String phone;

}
