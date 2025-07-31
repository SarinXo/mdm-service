package sarinxo.service.mdmservice.dto.service2;

import jakarta.validation.Valid;
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
public class UserPhoneUpdateService2Response {

    @Valid
    private UserPhoneUpdateService2ResponseBody body;

}
