package com.example.patientservice.kafka;

import com.example.grpc.patient.event.PatientEvent;
import com.example.patientservice.model.Patient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaProducer {

    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    public void sendMMessage(Patient patient) {
        PatientEvent patientEvent = PatientEvent.newBuilder().setPatientId(patient.getId().toString())
                .setEmail(patient.getEmail())
                .setEventType("PATIENT_CREATED")
                .build();

        try {
            kafkaTemplate.send("patient", patientEvent.toByteArray());
        }
        catch (Exception e) {
            log.error("Failed to send patient event to Kafka: {}", e.getMessage());
        }
    }
}
