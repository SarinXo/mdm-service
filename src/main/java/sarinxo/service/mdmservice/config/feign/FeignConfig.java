package sarinxo.service.mdmservice.config.feign;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "sarinxo.service.mdmservice.client")
public class FeignConfig {

}
