package com.beaconstrategists.clientcaseapi.repositories;

import com.beaconstrategists.clientcaseapi.model.entities.RmaCaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RmaCaseRepository extends JpaRepository<RmaCaseEntity, Long> {

    Optional<RmaCaseEntity> findByCaseNumber(String caseNumber);

    boolean existsByCaseNumber(String caseNumber);

    void deleteByCaseNumber(String caseNumber);
}
