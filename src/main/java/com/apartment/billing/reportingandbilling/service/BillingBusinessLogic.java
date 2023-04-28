package com.apartment.billing.reportingandbilling.service;

import com.apartment.billing.reportingandbilling.dto.UpsertBillingDetailsRequest;
import com.apartment.billing.reportingandbilling.dto.UpsertBillingDetailsResponse;
import com.apartment.billing.reportingandbilling.entity.Billing;
import com.apartment.billing.reportingandbilling.entity.FlatBillingDetails;
import com.apartment.billing.reportingandbilling.enums.BillStatus;
import com.apartment.billing.reportingandbilling.kafka.producer.BillProducer;
import com.apartment.billing.reportingandbilling.kafka.requests.BillProducerRequest;
import com.apartment.billing.reportingandbilling.repository.BillingRepository;
import com.apartment.billing.reportingandbilling.repository.FlatBillingDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class BillingBusinessLogic {

    @Autowired
    FlatBillingDetailsRepository billingDetailsRepository;
    @Autowired
    BillingRepository billingRepository;

    @Autowired
    BillProducer billProducer;

    @Transactional
    public UpsertBillingDetailsResponse createBillingDetails(UpsertBillingDetailsRequest request) {
        UpsertBillingDetailsResponse.UpsertBillingDetailsResponseBuilder responseBuilder = UpsertBillingDetailsResponse.builder();
        FlatBillingDetails flatBillingDetails = FlatBillingDetails.builder()
                .flatNumber(request.getFlatNumber())
                .rentAmount(request.getRent())
                .maintenanceAmount(request.getMaintenance())
                .maidAmount(request.getMaidAmount())
                .cookAmount(request.getCookAmount())
                .build();
        try {

            FlatBillingDetails billingDetails = billingDetailsRepository.save(flatBillingDetails);
            responseBuilder.flatNumber(request.getFlatNumber());
            responseBuilder.successMessage("Billing details successfully created with id "+billingDetails.getId());
            return responseBuilder.build();

        } catch (Exception e) {
            responseBuilder.faliureMessage(e.getMessage());
            responseBuilder.flatNumber(request.getFlatNumber());
            return responseBuilder.build();
        }

    }
    public void sendBillsToOwnersOrTenants(List<FlatBillingDetails> allBillingDetails) {
        allBillingDetails.stream().forEach(billingDetails -> {
            Billing billing = createBillingData(billingDetails);
            BillProducerRequest requestForBilling = createRequestForBilling(billing);
            billProducer.sendNotification(requestForBilling.toString());
        });
    }
    private BillProducerRequest createRequestForBilling(Billing billing) {
        return BillProducerRequest.builder()
                .billingId(billing.getBillingId())
                .flatNumber(billing.getBillingDetails().getFlatNumber())
                .month(billing.getMonth())
                .billCreatedTime(billing.getBillCreatedTime())
                .maidStatus(Optional.of(billing.getMaidStatus().toString()))
                .cookStatus(Optional.of(billing.getCookStatus().toString()))
                .rentStatus(billing.getRentStatus().toString())
                .maintenanceStatus(billing.getMaintenance_status().toString())
                .maintenance(billing.getBillingDetails().getMaintenanceAmount())
                .rent(billing.getBillingDetails().getRentAmount())
                .cook(Optional.of(billing.getBillingDetails().getCookAmount()))
                .maid(Optional.of(billing.getBillingDetails().getMaidAmount()))
                .billPaidTime(Optional.of(billing.getBillPaidTime()))
                .build();

    }
    @Transactional
    private Billing createBillingData(FlatBillingDetails billingDetails) {
        Billing billing = Billing.builder()
                .billCreatedTime(LocalDateTime.now())
                .maidStatus(BillStatus.DUE)
                .cookStatus(BillStatus.DUE)
                .maintenance_status(BillStatus.DUE)
                .rentStatus(BillStatus.DUE)
                .billingDetails(billingDetails)
                .month(LocalDateTime.now().getMonth().toString())
                .build();

        Billing savedBilling = billingRepository.save(billing);
        return savedBilling;

    }
}
