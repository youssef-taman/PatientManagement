package com.example.patientservice.service;


import com.example.grpc.billing.BillingResponse;
import com.example.grpc.billing.BillingServiceGrpc;
import com.example.patientservice.dto.PatientBillingSummaryDTO;
import com.example.patientservice.dto.PatientRequestDTO;
import com.example.patientservice.dto.PatientResponseDTO;
import com.example.patientservice.dto.PatientUpdateDTO;
import com.example.patientservice.exception.EmailAlreadyExistsException;
import com.example.patientservice.exception.ExceptionMessages;
import com.example.patientservice.exception.PatientNotFoundException;
import com.example.patientservice.grpc.BillingServiceGrpcClient;
import com.example.patientservice.kafka.KafkaProducer;
import com.example.patientservice.mapper.PatientMapper;
import com.example.patientservice.model.Patient;
import com.example.patientservice.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final BillingServiceGrpcClient billingServiceGrpcClient;
    private final KafkaProducer kafkaProducer;


    public List<PatientResponseDTO> getAllPatients() {
        List<Patient> patients = patientRepository.findAll();
        return patients.stream()
                .map(patientMapper::patientToPatientResponseDTO)
                .collect(Collectors.toList());
    }
	
    public PatientResponseDTO getPatientById(UUID patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException(ExceptionMessages.PATIENT_NOT_FOUND + patientId));
        return patientMapper.patientToPatientResponseDTO(patient);
    }

    public PatientResponseDTO getPatientByEmail(String email) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException(ExceptionMessages.PATIENT_NOT_FOUND + email));
        return patientMapper.patientToPatientResponseDTO(patient);
    }
	
    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException(ExceptionMessages.EMAIL_ALREADY_EXISTS + patientRequestDTO.getEmail());
        }
        Patient savedPatient = patientRepository.save(patientMapper.patientDTOToPatient(patientRequestDTO));
        kafkaProducer.sendMMessage(savedPatient);
        return patientMapper.patientToPatientResponseDTO(savedPatient);
    }

    public PatientResponseDTO updatePatient(UUID patientId, PatientUpdateDTO patientUpdateDTO) {
        Patient existingPatient = patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException(ExceptionMessages.PATIENT_NOT_FOUND + patientId));

        if (patientUpdateDTO.getName() != null) {
            existingPatient.setName(patientUpdateDTO.getName());
        }
        if (patientUpdateDTO.getAddress() != null) {
            existingPatient.setAddress(patientUpdateDTO.getAddress());
        }

        Patient savedPatient = patientRepository.save(existingPatient);

        return patientMapper.patientToPatientResponseDTO(savedPatient);
    }

    public void deletePatient(UUID patientId) {
        if (!patientRepository.existsById(patientId)) {
            throw new PatientNotFoundException(ExceptionMessages.PATIENT_NOT_FOUND + patientId);
        }
        patientRepository.deleteById(patientId);
    }

    public PatientBillingSummaryDTO getBillingInfo(UUID patientId) {
        return billingServiceGrpcClient.getBillingInfo(patientId);
    }

}

