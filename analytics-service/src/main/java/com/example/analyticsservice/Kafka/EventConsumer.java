package com.example.analyticsservice.Kafka;


import com.example.grpc.patient.event.PatientEvent;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class EventConsumer {

    @KafkaListener(topics = "patient", groupId = "analytics-service")
    public void consumeEvent(byte[] event){
        try {
            PatientEvent patientEvent = PatientEvent.parseFrom(event);
            log.info("Received patient event: {}", patientEvent);
        } catch (InvalidProtocolBufferException e) {
            log.error("Failed to parse patient event: {}", e.getMessage());
        }

    }

}
