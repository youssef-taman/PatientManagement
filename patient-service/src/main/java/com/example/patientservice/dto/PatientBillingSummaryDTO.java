package com.example.patientservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PatientBillingSummaryDTO {
    private String userId;
    private Double amountDue;
    private String dueDate;
}
