package ru.buzynnikov.Producer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${kafka.topic.partition.count}")
    private int partitionCount;
    @Bean
    public NewTopic newOrderTopic() {
        return TopicBuilder.name("web-logs").partitions(partitionCount).build();
    }
}
