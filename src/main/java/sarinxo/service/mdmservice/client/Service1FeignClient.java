package sarinxo.service.mdmservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import sarinxo.service.mdmservice.dto.service1.UserPhoneUpdateService1Request;
import sarinxo.service.mdmservice.dto.service1.UserPhoneUpdateService1Response;

@FeignClient(url = "${external-api.service1.url}", name = "service1Client")
public interface Service1FeignClient {

    @PostMapping("/update-phone")
    UserPhoneUpdateService1Response updatePhone(@RequestBody UserPhoneUpdateService1Request request);

}
