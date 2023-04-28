package com.apartment.billing.reportingandbilling.controller;

import com.apartment.billing.reportingandbilling.dto.UpsertBillingDetailsRequest;
import com.apartment.billing.reportingandbilling.dto.UpsertBillingDetailsResponse;
import com.apartment.billing.reportingandbilling.service.BillingBusinessLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillingController {

    @Autowired
    private BillingBusinessLogic billingBusinessLogic;

    @PostMapping("/createBillingDetails")
    public ResponseEntity<UpsertBillingDetailsResponse> createBillingDetails(@RequestBody UpsertBillingDetailsRequest billingDetailsRequest) {

        if(StringUtils.isEmpty(billingDetailsRequest.flatNumber)){
            UpsertBillingDetailsResponse flatNumberResponse = UpsertBillingDetailsResponse.builder().faliureMessage("Please provide flat number").build();
            return new ResponseEntity<>(flatNumberResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        UpsertBillingDetailsResponse billingDetailsResponse = billingBusinessLogic.createBillingDetails(billingDetailsRequest);
        return new ResponseEntity<>(billingDetailsResponse,HttpStatus.CREATED);
    }




}
