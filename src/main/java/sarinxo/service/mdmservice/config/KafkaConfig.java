package sarinxo.service.mdmservice.config;

import jakarta.el.MethodNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
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
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.DeserializationException;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.util.backoff.ExponentialBackOff;
import org.springframework.validation.Validator;
import sarinxo.service.mdmservice.config.property.KafkaTopicProperties;
import sarinxo.service.mdmservice.deserializer.UserEventDtoDeserializer;
import sarinxo.service.mdmservice.dto.kafka.UserEventKafkaDto;
import sarinxo.service.mdmservice.utils.ExceptionUtil;

import java.util.Map;

@Slf4j
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
    public ConcurrentKafkaListenerContainerFactory<String, UserEventKafkaDto> userEventListenerContainerFactory(
            ConsumerFactory<String, UserEventKafkaDto> userEventConsumerFactory,
            DefaultErrorHandler mdmErrorHandler
    ) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, UserEventKafkaDto>();
        factory.setConsumerFactory(userEventConsumerFactory);
        factory.setCommonErrorHandler(mdmErrorHandler);
        factory.getContainerProperties().setPollTimeout(500);

        return factory;
    }

    @Bean
    public ConsumerFactory<String, UserEventKafkaDto> userEventConsumerFactory(
            KafkaProperties kafkaProperties,
            Validator validator
    ) {
        Map<String, Object> props = kafkaProperties.buildConsumerProperties();

        var keyDeserializer = new StringDeserializer();
        var valDeserializer = new ErrorHandlingDeserializer<>(new UserEventDtoDeserializer());
        valDeserializer.setValidator(validator);

        var consumerFactory = new DefaultKafkaConsumerFactory<String, UserEventKafkaDto>(props);
        consumerFactory.setValueDeserializer(valDeserializer);
        consumerFactory.setKeyDeserializer(keyDeserializer);

        return consumerFactory;

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
    public DefaultErrorHandler mdmErrorHandler(
            KafkaTemplate<String, String> dltKafkaTemplate,
            KafkaTopicProperties kProps
    ) {
        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(
                dltKafkaTemplate,
                (record, ex) -> new TopicPartition(record.topic() + kProps.dltTopicSuffix(), record.partition())
        );

        ExponentialBackOff backOff = new ExponentialBackOff(500L, 1.25);
        backOff.setMaxAttempts(5);
        backOff.setMaxInterval(3_000L);

        DefaultErrorHandler errorHandler = new DefaultErrorHandler(recoverer, backOff);

        errorHandler.addNotRetryableExceptions(
                DeserializationException.class,
                IllegalArgumentException.class,
                ClassNotFoundException.class,
                MethodNotFoundException.class
        );

        errorHandler.setRetryListeners((record, ex, deliveryAttempt) ->
                log.warn("Retry attempt {} for record {} failed with exception {}",
                        deliveryAttempt, record.value(), ExceptionUtil.causeChain(ex)));

        return errorHandler;
    }

}
