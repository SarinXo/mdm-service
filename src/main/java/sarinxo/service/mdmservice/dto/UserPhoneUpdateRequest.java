package sarinxo.service.mdmservice.dto;

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
public class UserPhoneUpdateRequest {

    private String id;
    private String systemId;
    private List<UserPhoneUpdateEvent> events;

}
