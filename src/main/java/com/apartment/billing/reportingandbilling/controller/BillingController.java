package com.apartment.billing.reportingandbilling.controller;

import com.apartment.billing.reportingandbilling.dto.GetBillingDetailsRequest;
import com.apartment.billing.reportingandbilling.dto.GetBillingDetailsResponse;
import com.apartment.billing.reportingandbilling.dto.UpsertBillingDetailsRequest;
import com.apartment.billing.reportingandbilling.dto.UpsertBillingDetailsResponse;
import com.apartment.billing.reportingandbilling.entity.FlatBillingDetails;
import com.apartment.billing.reportingandbilling.service.BillingBusinessLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/billing")
public class BillingController {

    @Autowired
    private BillingBusinessLogic billingBusinessLogic;

    @PostMapping("/createBillingDetails")
    public ResponseEntity<UpsertBillingDetailsResponse> createBillingDetails(@RequestBody UpsertBillingDetailsRequest billingDetailsRequest) {

        if(StringUtils.isEmpty(billingDetailsRequest.getFlatNumber())){
            UpsertBillingDetailsResponse flatNumberResponse = UpsertBillingDetailsResponse.builder().faliureMessage("Please provide flat number").build();
            return new ResponseEntity<>(flatNumberResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        UpsertBillingDetailsResponse billingDetailsResponse = billingBusinessLogic.createBillingDetails(billingDetailsRequest);
        return new ResponseEntity<>(billingDetailsResponse,HttpStatus.CREATED);
    }

    @PutMapping("/updateBillingDetails")
    public ResponseEntity<UpsertBillingDetailsResponse> updateBillingDetails(@RequestBody UpsertBillingDetailsRequest billingDetailsRequest) {

        if(billingDetailsRequest.getId() == null){
            if (StringUtils.isEmpty(billingDetailsRequest.getFlatNumber())) {
                UpsertBillingDetailsResponse flatNumberResponse = UpsertBillingDetailsResponse.builder().faliureMessage("Please provide flat number or billing Id for update").build();
                return new ResponseEntity<>(flatNumberResponse, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        UpsertBillingDetailsResponse updateBillingDetailsResponse = billingBusinessLogic.updateBillingDetails(billingDetailsRequest);
        return new ResponseEntity<>(updateBillingDetailsResponse,HttpStatus.CREATED);
    }

    @PostMapping("/getBillingDetails")
    public ResponseEntity<GetBillingDetailsResponse> getBillingDetails(@RequestBody GetBillingDetailsRequest billingDetailsRequest) {

        if(billingDetailsRequest.getId() == null){
            if (StringUtils.isEmpty(billingDetailsRequest.getFlatNumber())) {
                GetBillingDetailsResponse getBillingDetailsResponse = GetBillingDetailsResponse.builder().faliureMessage("Please provide flat number or billing Id for get").build();
                return new ResponseEntity<>(getBillingDetailsResponse, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        GetBillingDetailsResponse billingDetailsResponse = billingBusinessLogic.getBillingDetails(billingDetailsRequest);
        return new ResponseEntity<>(billingDetailsResponse,HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<FlatBillingDetails>> getAllFlatBillingDetails(@RequestParam("pageSize") Integer pageSize,
                                                                             @RequestParam("pageNo") Integer pageNo) {
        List<FlatBillingDetails> allBilling = billingBusinessLogic.getAllBillingDetails(pageSize,pageNo);
        return new ResponseEntity<>(allBilling,HttpStatus.OK);
    }


}
