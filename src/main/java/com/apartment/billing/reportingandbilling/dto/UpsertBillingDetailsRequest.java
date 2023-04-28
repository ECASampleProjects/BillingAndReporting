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

    public String flatNumber;

    public Long rent;

    public Long cookAmount;

    public Long maidAmount;

    public Long maintenance;

}
