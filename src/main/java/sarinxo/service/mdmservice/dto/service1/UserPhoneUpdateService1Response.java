package sarinxo.service.mdmservice.dto.service1;

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
public class UserPhoneUpdateService1Response {

    @Valid
    private UserPhoneUpdateService1ResponseBody body;

}
