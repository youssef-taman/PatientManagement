package com.example.patientservice.exception;

public class BillingServiceUnavailableException extends ExternalServiceException {
    public BillingServiceUnavailableException(String serviceName, String message, Throwable cause) {
        super(serviceName, message, cause);
    }

}
