package com.apartment.billing.reportingandbilling.entity;

import com.apartment.billing.reportingandbilling.enums.BillStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@Table(name="billing")
public class Billing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long billingId;

    //@OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    //@JoinColumn(name = "billing_details_id", referencedColumnName = "id")
    @OneToOne
    private FlatBillingDetails billingDetails;

    @Column(name="billing_month")
    private String month;

    @Column(name="bill_paid_timestamp")
    private LocalDateTime billPaidTime;

    @Column(name="bill_create_timestamp")
    private LocalDateTime billCreatedTime;

    @Column(name="rent_status")
    @Enumerated(EnumType.STRING)
    private BillStatus rentStatus;

    @Column(name="maintenance_status")
    @Enumerated(EnumType.STRING)
    private BillStatus maintenance_status;

    @Column(name="cook_status")
    @Enumerated(EnumType.STRING)
    private BillStatus cookStatus;

    @Column(name="maid_status")
    @Enumerated(EnumType.STRING)
    private BillStatus maidStatus;
}
