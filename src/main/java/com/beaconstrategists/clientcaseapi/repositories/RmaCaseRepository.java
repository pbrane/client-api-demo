package com.beaconstrategists.clientcaseapi.repositories;

import com.beaconstrategists.clientcaseapi.model.entities.RmaCaseEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RmaCaseRepository extends CrudRepository<RmaCaseEntity, Long> {

    Optional<RmaCaseEntity> findByCaseNumber(String caseNumber);

    boolean existsByCaseNumber(String caseNumber);

}
