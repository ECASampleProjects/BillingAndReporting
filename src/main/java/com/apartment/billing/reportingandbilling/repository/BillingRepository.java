package com.apartment.billing.reportingandbilling.repository;

import com.apartment.billing.reportingandbilling.entity.Billing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillingRepository extends JpaRepository<Billing,Long> {
}
