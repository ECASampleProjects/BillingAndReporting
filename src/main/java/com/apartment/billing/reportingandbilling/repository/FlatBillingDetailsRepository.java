package com.apartment.billing.reportingandbilling.repository;

import com.apartment.billing.reportingandbilling.entity.FlatBillingDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FlatBillingDetailsRepository extends JpaRepository<FlatBillingDetails,Long> {
    Optional<FlatBillingDetails> findByFlatNumber(String flatNumber);
}
