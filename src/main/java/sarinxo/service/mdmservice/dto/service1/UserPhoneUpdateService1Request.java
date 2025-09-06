package sarinxo.service.mdmservice.dto.service1;

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
public class UserPhoneUpdateService1Request {

    private RequestMeta meta;
    private UserPhoneUpdateService1RequestBody body;

}
