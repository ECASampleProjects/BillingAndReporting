package com.apartment.billing.reportingandbilling.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@Table(name="flat_billing_details")
public class FlatBillingDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "flat_number")
    private String flatNumber;

    @Column(name="maintenance_amount")
    private Long maintenanceAmount;

    @Column(name="rent_amount")
    private Long rentAmount;

    @Column(name="maid_amount")
    private Long maidAmount;

    @Column(name="cook_amount")
    private Long cookAmount;

}
