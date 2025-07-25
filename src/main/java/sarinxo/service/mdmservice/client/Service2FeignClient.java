package sarinxo.service.mdmservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import sarinxo.service.mdmservice.config.feign.DeignServiceConfig;

@FeignClient(url = "${external-api.service2.url}", name = "service2Client", configuration = DeignServiceConfig.class)
public interface Service2FeignClient {

    @PostMapping("/user/update/phone")


}
