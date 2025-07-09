package sarinxo.service.mdmservice.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.retrytopic.RetryTopicConfiguration;
import org.springframework.kafka.retrytopic.RetryTopicConfigurationBuilder;
import sarinxo.service.mdmservice.config.property.KafkaTopicProperties;
import sarinxo.service.mdmservice.deserializer.UserEventDtoDeserializer;
import sarinxo.service.mdmservice.dto.UserEventDto;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

    private final KafkaTopicProperties kafkaTopicProperties;

    /**
     * Установка фабрики сообщений в прослушивателя Kafka.
     *
     * @param userEventConsumerFactory фабрика с измененным десериализатором.
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UserEventDto> userEventListenerContainerFactory(
            ConsumerFactory<String, UserEventDto> userEventConsumerFactory
    ) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, UserEventDto>();
        factory.setConsumerFactory(userEventConsumerFactory);
        factory.getContainerProperties().setPollTimeout(500);

        return factory;
    }

    @Bean
    public ConsumerFactory<String, UserEventDto> userEventConsumerFactory(KafkaProperties kafkaProperties) {
        Map<String, Object> props = kafkaProperties.buildConsumerProperties();
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, UserEventDtoDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    @SuppressWarnings("unchecked")
    public KafkaTemplate<String, String> dltKafkaTemplate(ProducerFactory<String, String> pf) {
        return new KafkaTemplate<>(pf);
    }

    @Bean
    public NewTopic mdmChangePhoneInV1Topic() {
        return TopicBuilder
                .name(kafkaTopicProperties.userEvent().topicName())
                .partitions(3)
                .compact()
                .build();
    }

    @Bean
    public NewTopic mdmChangePhoneInV1TopicDlq() {
        String dltTopicName = kafkaTopicProperties.userEvent().topicName() + kafkaTopicProperties.dltTopicSuffix();

        return TopicBuilder
                .name(dltTopicName)
                .partitions(3)
                .build();
    }

    @Bean
    public RetryTopicConfiguration kafkaRetryConfig(
            KafkaTemplate<String, String> dltKafkaTemplate
    ) {
        return RetryTopicConfigurationBuilder
                .newInstance()
                .maxAttempts(5)
                .exponentialBackoff(500L, 1.25, 3_000L)
                .dltSuffix(kafkaTopicProperties.dltTopicSuffix())
                .create(dltKafkaTemplate);
    }

}
