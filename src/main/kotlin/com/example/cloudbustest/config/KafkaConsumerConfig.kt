package com.example.cloudbustest.config

import com.example.cloudbustest.data.KafkaData
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer
import org.springframework.kafka.support.serializer.JsonDeserializer

@EnableKafka
@Configuration
class KafkaConsumerConfig(
    @Value("localhost:8888")
    private val bootstrapServer : String
) {

    fun consumerFactory() : ConsumerFactory<String, KafkaData>{
        val configs = mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServer,
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to JsonDeserializer::class.java,
            JsonDeserializer.TRUSTED_PACKAGES to "*",
        )
        return DefaultKafkaConsumerFactory(configs)
    }

    @Bean
    fun kafkaDataListenerFactory() : ConcurrentKafkaListenerContainerFactory<String, KafkaData> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, KafkaData>()
        factory.consumerFactory = consumerFactory()
        factory.setConcurrency(3)
        return factory
    }
}