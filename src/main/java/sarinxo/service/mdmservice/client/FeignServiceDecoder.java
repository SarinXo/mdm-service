package sarinxo.service.mdmservice.client;

import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.Decoder;
import sarinxo.service.mdmservice.dto.service1.ServiceStatus;
import sarinxo.service.mdmservice.dto.service1.UserPhoneUpdateService1Response;

import java.io.IOException;
import java.lang.reflect.Type;

public class FeignServiceDecoder implements Decoder {

    private final Decoder decoder;

    public FeignServiceDecoder(Decoder decoder) {
        this.decoder = decoder;
    }

    @Override
    public Object decode(Response response, Type type) throws IOException, FeignException {
        Object decoded = decoder.decode(response, type);

        if (decoded instanceof UserPhoneUpdateService1Response dto) {
            if (ServiceStatus.ERROR.equals(dto.getBody().getStatus())) {
                throw new RetryableException(
                        response.status(),
                        "Service response status = ERROR",
                        response.request().httpMethod(),
                        (Long)null,
                        response.request());
            }
        }

        return decoded;
    }

}
