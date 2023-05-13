package com.apartment.billing.reportingandbilling.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpsertBillingDetailsRequest {

    private String flatNumber;

    private Long rent;

    private Long cookAmount;

    private Long maidAmount;

    private Long maintenance;

    private Long id;

}
