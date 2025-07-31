package sarinxo.service.mdmservice.dto.service1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserPhoneUpdateService1RequestBody {

    private UUID id;
    private String guid;
    private String phone;

}
