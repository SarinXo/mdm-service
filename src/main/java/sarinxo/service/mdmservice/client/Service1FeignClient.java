package sarinxo.service.mdmservice.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(url = "", name = "service1Client")
public interface Service1FeignClient {
}
