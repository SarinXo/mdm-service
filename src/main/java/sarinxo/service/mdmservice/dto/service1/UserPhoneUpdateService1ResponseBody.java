package sarinxo.service.mdmservice.dto.service1;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.Nullable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserPhoneUpdateService1ResponseBody {

    @NotEmpty(message = "Field 'id' can't be empty")
    private String id;
    @NotNull(message = "Field 'status' can't be null")
    private ServiceStatus status;
    @Getter(onMethod_ = @Nullable)
    private String errorMessage;

}
