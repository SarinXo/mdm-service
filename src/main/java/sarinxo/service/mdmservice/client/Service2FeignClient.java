package sarinxo.service.mdmservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(url = "${external-api.service2.url}", name = "service2Client")
public interface Service2FeignClient {

    @PostMapping("/user/update/phone")


}
