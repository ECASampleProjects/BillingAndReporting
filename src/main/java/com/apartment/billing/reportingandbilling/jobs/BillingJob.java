package com.apartment.billing.reportingandbilling.jobs;

import com.apartment.billing.reportingandbilling.entity.Billing;
import com.apartment.billing.reportingandbilling.entity.FlatBillingDetails;
import com.apartment.billing.reportingandbilling.repository.BillingRepository;
import com.apartment.billing.reportingandbilling.repository.FlatBillingDetailsRepository;
import com.apartment.billing.reportingandbilling.service.BillingBusinessLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BillingJob {

    @Autowired
    FlatBillingDetailsRepository billingDetailsRepository;

    @Autowired
    BillingBusinessLogic billingBusinessLogic;
    
    @Scheduled(cron = "0 0 23 28-31 * ?")
    //@Scheduled(fixedDelay = 120000) // 120000 milliseconds = 2 minutes
    public void billingJob() {
        List<FlatBillingDetails> allBillingDetails = billingDetailsRepository.findAll();
        billingBusinessLogic.sendBillsToOwnersOrTenants(allBillingDetails);
    }
}
