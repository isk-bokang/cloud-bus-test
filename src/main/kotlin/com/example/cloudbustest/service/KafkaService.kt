package com.example.cloudbustest.service

import lombok.RequiredArgsConstructor
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class KafkaService(
    private val kafkaTemplate: KafkaTemplate<String, String>
) {

    fun sendMessage(msg : String){
        kafkaTemplate.send("dummy", msg)
    }

    @KafkaListener(topics = ["dummy"], groupId = "kafka-test")
    fun consume(msg: String){
        println("consume : $msg")
    }
}