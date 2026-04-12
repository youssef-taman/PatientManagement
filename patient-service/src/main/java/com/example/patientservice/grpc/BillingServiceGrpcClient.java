package com.example.patientservice.grpc;

import com.example.grpc.billing.BillingRequest;
import com.example.grpc.billing.BillingResponse;
import com.example.grpc.billing.BillingServiceGrpc;
import com.example.patientservice.dto.PatientBillingSummaryDTO;
import com.example.patientservice.exception.BillingServiceUnavailableException;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.example.patientservice.exception.ExceptionMessages;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class BillingServiceGrpcClient {

    private final BillingServiceGrpc.BillingServiceBlockingStub billingServiceStub;

    public BillingServiceGrpcClient(
            @Value("${grpc.billing.service.host:localhost}") String host,
            @Value("${grpc.billing.service.port:9090}") int port
    ) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        this.billingServiceStub = BillingServiceGrpc.newBlockingStub(channel);
    }

    public PatientBillingSummaryDTO getBillingInfo(UUID patientId) {
        BillingRequest request = BillingRequest.newBuilder().setUserId(patientId.toString()).build();
        try {
            BillingResponse response = billingServiceStub.
                    withDeadlineAfter(3, TimeUnit.SECONDS).
                    getBillingInfo(request);

            return new PatientBillingSummaryDTO(
                    response.getUserId(),
                    response.getAmountDue(),
                    response.getDueDate()
            );

        } catch (StatusRuntimeException e) {
            throw new BillingServiceUnavailableException("billing-service", ExceptionMessages.BILLING_SERVICE_IS_DOWN, e);
        }
    }



}
