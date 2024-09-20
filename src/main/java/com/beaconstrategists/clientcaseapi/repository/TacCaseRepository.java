package com.beaconstrategists.clientcaseapi.repository;

import com.beaconstrategists.clientcaseapi.model.TacCase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TacCaseRepository extends JpaRepository<TacCase, Long> {
    Optional<TacCase> findByCaseNumber(String caseNumber);
}
