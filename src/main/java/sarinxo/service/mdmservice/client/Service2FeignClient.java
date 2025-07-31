package sarinxo.service.mdmservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import sarinxo.service.mdmservice.config.feign.DeignServiceConfig;
import sarinxo.service.mdmservice.dto.service2.UserPhoneUpdateService2Request;
import sarinxo.service.mdmservice.dto.service2.UserPhoneUpdateService2Response;

@FeignClient(url = "${external-api.service2.url}", name = "service2Client", configuration = DeignServiceConfig.class)
public interface Service2FeignClient {

    @PostMapping("/user/update/phone")
    UserPhoneUpdateService2Response updatePhone(@RequestBody UserPhoneUpdateService2Request request);

}
