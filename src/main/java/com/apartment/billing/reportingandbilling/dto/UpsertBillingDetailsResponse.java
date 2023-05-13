package com.apartment.billing.reportingandbilling.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpsertBillingDetailsResponse {

    private String successMessage;

    private String faliureMessage;

    private String flatNumber;
}
