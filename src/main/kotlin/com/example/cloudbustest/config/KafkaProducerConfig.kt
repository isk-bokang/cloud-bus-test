package com.example.cloudbustest.config



import com.example.cloudbustest.data.KafkaData
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer


@Configuration
class KafkaProducerConfig(
    @Value("localhost:8888")
    private val bootstrapServer : String
) {
    @Bean
    fun producerFactory() : ProducerFactory<String, KafkaData> {
        val configs = mutableMapOf<String, Any>()
        configs[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapServer
        configs[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        configs[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = JsonSerializer::class.java

        return DefaultKafkaProducerFactory(configs)
    }

    @Bean
    fun kafkaTemplate() : KafkaTemplate<String, KafkaData>{
        return KafkaTemplate(producerFactory())
    }
}
