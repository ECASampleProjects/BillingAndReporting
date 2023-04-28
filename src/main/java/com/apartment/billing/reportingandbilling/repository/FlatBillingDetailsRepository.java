package com.apartment.billing.reportingandbilling.repository;

import com.apartment.billing.reportingandbilling.entity.FlatBillingDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlatBillingDetailsRepository extends JpaRepository<FlatBillingDetails,Long> {
}
