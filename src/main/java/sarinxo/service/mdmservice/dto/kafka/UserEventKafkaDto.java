package sarinxo.service.mdmservice.dto.kafka;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserEventKafkaDto {

    @NotNull(message = "Field 'id' can't be null")
    private UUID id;
    @NotNull(message = "Field 'type' can't be null")
    private MdmEventType type;
    @NotNull(message = "Field 'guid' can't be null")
    @Size(min = 32, max = 32, message = "Field 'guid' must have 32 symbols")
    private String guid;
    @NotNull(message = "Field 'phone' can't be null")
    @Pattern(regexp = "^\\+7\\d{10}$", message = "Field 'phone' must correspond to a Russian phone number and start with +7")
    private String phone;

}
