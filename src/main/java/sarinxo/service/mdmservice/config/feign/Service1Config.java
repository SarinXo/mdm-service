package sarinxo.service.mdmservice.config.feign;

import feign.codec.Decoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sarinxo.service.mdmservice.client.Service1Decoder;

@Configuration
public class Service1Config {

    @Bean
    public Service1Decoder feignDecoder(Decoder decoder) {
        return new Service1Decoder(decoder);
    }
}
