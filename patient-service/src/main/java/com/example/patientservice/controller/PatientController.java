package com.example.patientservice.controller;

import com.example.patientservice.dto.PatientRequestDTO;
import com.example.patientservice.dto.PatientResponseDTO;
import com.example.patientservice.dto.PatientUpdateDTO;
import com.example.patientservice.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
@Tag(name = "Patient Management", description = "Endpoints for managing patients")
public class PatientController {

    private final PatientService patientService;

    @Operation(summary = "Get all patients", description = "Returns a list of all patients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
    })
    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> getPatients() {
        return ResponseEntity.status(HttpStatus.OK).body(
                patientService.getAllPatients());
    }

    @GetMapping("/{patientId}")
    @Operation(summary = "Get a patient by ID", description = "Returns a single patient by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved patient"),
            @ApiResponse(responseCode = "404", description = "Patient not found"),
    })
    public ResponseEntity<PatientResponseDTO> getPatientById(
            @Parameter(description = "The ID of the patient to retrieve")
            @PathVariable("patientId") String patientId) {
        return ResponseEntity.status(HttpStatus.OK).body(
                patientService.getPatientById(UUID.fromString(patientId)));
    }
	
    @GetMapping("/email/{email}")
    @Operation(summary = "Get a patient by email", description = "Returns a single patient by their email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved patient"),
            @ApiResponse(responseCode = "404", description = "Patient not found"),
    })
    public ResponseEntity<PatientResponseDTO> getPatientByEmail(
            @Parameter(description = "The email of the patient to retrieve")
            @PathVariable("email") String email) {
        return ResponseEntity.status(HttpStatus.OK).body(
                patientService.getPatientByEmail(email));
    }
	
    @PostMapping
    @Operation(summary = "Create a new patient", description = "Creates a new patient and returns the created patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Patient created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
    })
    public ResponseEntity<PatientResponseDTO> savePatient(@Valid @RequestBody PatientRequestDTO patientRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                (patientService.createPatient(patientRequestDTO)));
    }

    @Operation(summary = "Update a patient", description = "Updates a patient's information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "404", description = "Patient not found"),
    })
    @PatchMapping("/{patientId}")
    public ResponseEntity<PatientResponseDTO> updatePatient(
            @Parameter(description = "The ID of the patient to update")
            @PathVariable("patientId") String patientId,
            @RequestBody PatientUpdateDTO patientUpdateDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(
                patientService.updatePatient(UUID.fromString(patientId), patientUpdateDTO));
    }
	
    @DeleteMapping("/{patientId}")
    @Operation(summary = "Delete a patient", description = "Deletes a patient by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Patient deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Patient not found"),
    })
    public ResponseEntity<Void> deletePatient(
            @Parameter(description = "The ID of the patient to delete")
            @PathVariable("patientId") String patientId) {
        patientService.deletePatient(UUID.fromString(patientId));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
