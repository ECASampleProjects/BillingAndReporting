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

    private Optional<LocalDateTime> billPaidTime;

    private LocalDateTime billCreatedTime;

    private String rentStatus;

    private String maintenanceStatus;

    private Optional<String> cookStatus;

    private Optional<String> maidStatus;

    private Long maintenance;

    private Long rent;

    private Optional<Long> maid;

    private Optional<Long> cook;

}
