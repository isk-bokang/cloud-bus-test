package com.example.cloudbustest.config

import com.example.cloudbustest.data.KafkaData
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer
import org.springframework.kafka.support.serializer.JsonDeserializer

@Configuration
class KafkaConsumerConfig(
    @Value("localhost:8888")
    private val bootstrapServer : String
) {
    @Bean
    fun consumerFactory() : ConsumerFactory<String, KafkaData>{
        val configs = mutableMapOf<String, Any>()
        configs[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapServer
        configs[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        configs[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer(KafkaData::class.java)::class.java
        configs[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = ErrorHandlingDeserializer::class.java
        configs[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = ErrorHandlingDeserializer::class.java
        configs[JsonDeserializer.TRUSTED_PACKAGES] = "*"
        return DefaultKafkaConsumerFactory(configs)
    }

    @Bean
    fun kafkaDataListenerFactory() : ConcurrentKafkaListenerContainerFactory<String, KafkaData> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, KafkaData>()
        factory.consumerFactory = consumerFactory()
        return factory
    }
}