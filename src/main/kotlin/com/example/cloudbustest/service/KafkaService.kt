package com.example.cloudbustest.service

import com.example.cloudbustest.data.KafkaData
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Service

@Service
class KafkaService(
    @Autowired
    private val kafkaTemplate: KafkaTemplate<String, KafkaData>
) {

    fun sendMessage(msg : String){
        val tmpData = KafkaData(name = "KAFKA", id = 52)

        kafkaTemplate.send("dummy", tmpData)
    }

    @KafkaListener(topics = ["dummy"], groupId = "kafka-test", containerFactory = "kafkaDataListenerFactory")
    fun consume(@Payload msg : String){
        println("consume : $msg")
    }
}