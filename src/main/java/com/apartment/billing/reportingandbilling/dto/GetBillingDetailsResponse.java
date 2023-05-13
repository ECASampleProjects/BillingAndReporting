package com.apartment.billing.reportingandbilling.dto;


import com.apartment.billing.reportingandbilling.entity.FlatBillingDetails;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetBillingDetailsResponse {

    private FlatBillingDetails flatBillingDetails;

    private String faliureMessage;
}
