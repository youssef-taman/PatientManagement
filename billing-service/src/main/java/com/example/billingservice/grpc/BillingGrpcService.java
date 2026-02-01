package com.example.billingservice.grpc;

import com.example.grpc.billing.BillingRequest;
import com.example.grpc.billing.BillingResponse;
import com.example.grpc.billing.BillingServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class BillingGrpcService extends BillingServiceGrpc.BillingServiceImplBase {


    @Override
    public void getBillingInfo(BillingRequest request, StreamObserver<BillingResponse> responseObserver) {
        // Mock processing logic
        BillingResponse response = BillingResponse.newBuilder().setUserId(request.getUserId()).setAmountDue(100.0).setDueDate("2024-12-31").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }


}
