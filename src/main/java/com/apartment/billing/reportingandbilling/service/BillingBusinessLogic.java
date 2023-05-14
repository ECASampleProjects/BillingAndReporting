package com.apartment.billing.reportingandbilling.service;

import com.apartment.billing.reportingandbilling.dto.GetBillingDetailsRequest;
import com.apartment.billing.reportingandbilling.dto.GetBillingDetailsResponse;
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
import org.springframework.data.domain.PageRequest;
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
                .maidStatus(billing.getMaidStatus() != null ? billing.getMaidStatus().toString() : null)
                .cookStatus(billing.getCookStatus() != null ? billing.getCookStatus().toString() : null)
                .rentStatus(billing.getRentStatus().toString())
                .maintenanceStatus(billing.getMaintenance_status().toString())
                .maintenance(billing.getBillingDetails().getMaintenanceAmount())
                .rent(billing.getBillingDetails().getRentAmount())
                .cook(billing.getBillingDetails().getCookAmount() != null ? billing.getBillingDetails().getCookAmount() : null)
                .maid(billing.getBillingDetails().getMaidAmount() != null ? billing.getBillingDetails().getMaidAmount() : null)
                .billPaidTime(billing.getBillPaidTime() != null ? billing.getBillPaidTime() : null)
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

    @Transactional
    public UpsertBillingDetailsResponse updateBillingDetails(UpsertBillingDetailsRequest billingDetailsRequest) {
        Optional<FlatBillingDetails> billingDetails;
        UpsertBillingDetailsResponse.UpsertBillingDetailsResponseBuilder responseBuilder = UpsertBillingDetailsResponse.builder();
        if (billingDetailsRequest.getId() != null) {
            billingDetails = billingDetailsRepository.findById(billingDetailsRequest.getId());
        } else {
            billingDetails = billingDetailsRepository.findByFlatNumber(billingDetailsRequest.getFlatNumber());
        }

        if (!billingDetails.isPresent()) {
            responseBuilder.faliureMessage("No flat present for this id or flat number");
            return responseBuilder.build();
        }

        FlatBillingDetails updateBillingDetails = createUpdateDataForBillingDetails(billingDetailsRequest,billingDetails);

        try {

            FlatBillingDetails savedBillingDetails = billingDetailsRepository.save(updateBillingDetails);
            responseBuilder.flatNumber(billingDetailsRequest.getFlatNumber());
            responseBuilder.successMessage("Billing details successfully created with id "+savedBillingDetails.getId());
            return responseBuilder.build();

        } catch (Exception e) {
            responseBuilder.faliureMessage(e.getMessage());
            responseBuilder.flatNumber(billingDetailsRequest.getFlatNumber());
            return responseBuilder.build();
        }

    }

    private FlatBillingDetails createUpdateDataForBillingDetails(UpsertBillingDetailsRequest billingDetailsRequest, Optional<FlatBillingDetails> billingDetails) {

        FlatBillingDetails flatBillingDetails = FlatBillingDetails.builder()
                .maintenanceAmount(billingDetailsRequest.getMaintenance() != null ? billingDetailsRequest.getMaintenance() : billingDetails.get().getMaintenanceAmount())
                .cookAmount(billingDetailsRequest.getCookAmount() != null ? billingDetailsRequest.getCookAmount() : billingDetails.get().getCookAmount())
                .rentAmount(billingDetailsRequest.getRent() != null ? billingDetailsRequest.getRent() : billingDetails.get().getRentAmount())
                .build();
        return  flatBillingDetails;
    }

    public GetBillingDetailsResponse getBillingDetails(GetBillingDetailsRequest billingDetailsRequest) {
        Optional<FlatBillingDetails> billingDetails;
        GetBillingDetailsResponse.GetBillingDetailsResponseBuilder responseBuilder = GetBillingDetailsResponse.builder();
        if (billingDetailsRequest.getId() != null) {
            billingDetails = billingDetailsRepository.findById(billingDetailsRequest.getId());
        } else {
            billingDetails = billingDetailsRepository.findByFlatNumber(billingDetailsRequest.getFlatNumber());
        }
        if (!billingDetails.isPresent()) {
            responseBuilder.faliureMessage("No flat present for this id or flat number");
            return responseBuilder.build();
        }
        return responseBuilder.flatBillingDetails(billingDetails.get()).build();
    }

    public List<FlatBillingDetails> getAllBillingDetails(Integer pageSize, Integer pageNo) {
        return billingDetailsRepository.findAll(PageRequest.of(pageNo,pageSize)).toList();
    }
}
