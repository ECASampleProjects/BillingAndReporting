package com.apartment.billing.reportingandbilling.kafka.requests;

import lombok.*;
import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BillProducerRequest {

    private Long billingId;

    private String flatNumber;

    private String month;

    private LocalDateTime billPaidTime;

    private LocalDateTime billCreatedTime;

    private String rentStatus;

    private String maintenanceStatus;

    private String cookStatus;

    private String maidStatus;

    private Long maintenance;

    private Long rent;

    private Long maid;

    private Long cook;

}
