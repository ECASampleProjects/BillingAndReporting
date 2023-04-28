package com.apartment.billing.reportingandbilling.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpsertBillingDetailsResponse {

    public String successMessage;

    public String faliureMessage;

    public String flatNumber;
}
