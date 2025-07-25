package sarinxo.service.mdmservice.config.feign;

import feign.codec.Decoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sarinxo.service.mdmservice.client.FeignServiceDecoder;

@Configuration
public class DeignServiceConfig {

    @Bean
    public FeignServiceDecoder feignDecoder(Decoder decoder) {
        return new FeignServiceDecoder(decoder);
    }
}
