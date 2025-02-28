package vn.hoanggiang.jobhunter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;
import vn.hoanggiang.jobhunter.domain.JobCreatedEvent;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public ConsumerFactory<String, JobCreatedEvent> jobEventConsumerFactory(ObjectMapper objectMapper) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "job-notification-service");

        JsonDeserializer<JobCreatedEvent> deserializer = new JsonDeserializer<>(JobCreatedEvent.class, objectMapper);
        deserializer.addTrustedPackages("com.jobhunter.models");

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                deserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, JobCreatedEvent> kafkaListenerContainerFactory(ObjectMapper objectMapper) {
        ConcurrentKafkaListenerContainerFactory<String, JobCreatedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(jobEventConsumerFactory(objectMapper));
        factory.setCommonErrorHandler(errorHandler());
        return factory;
    }

    private DefaultErrorHandler errorHandler() {
        FixedBackOff fixedBackOff = new FixedBackOff(1000L, 3);
        return new DefaultErrorHandler(
                (record, exception) -> {
                    System.err.println("Error processing record: " + record + ", error: " + exception.getMessage());
                },
                fixedBackOff
        );
    }
}

