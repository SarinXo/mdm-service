package sarinxo.service.mdmservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class UserPhoneUpdateService1Body {

    @NotNull(message = "Field 'guid' can't be null")
    @Size(min = 32, max = 32, message = "Field 'guid' must have 32 symbols")
    String id;
    @NotNull(message = "Field 'status' can't be null")
    ServiceStatus status;
    @Getter(onMethod_ = @Nullable)
    String errorMessage;

}
