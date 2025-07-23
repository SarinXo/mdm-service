package sarinxo.service.mdmservice.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(url = "${external-api.service1.url}", name = "service1Client")
public interface Service1FeignClient {


}
