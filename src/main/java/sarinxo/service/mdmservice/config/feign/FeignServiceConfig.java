package sarinxo.service.mdmservice.config.feign;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sarinxo.service.mdmservice.client.FeignServiceDecoder;

@Configuration
@RequiredArgsConstructor
public class FeignServiceConfig {

    private final ObjectFactory<HttpMessageConverters> messageConverters;

    @Bean
    public FeignServiceDecoder feignDecoder() {
        return new FeignServiceDecoder(new SpringDecoder(messageConverters));
    }

}
