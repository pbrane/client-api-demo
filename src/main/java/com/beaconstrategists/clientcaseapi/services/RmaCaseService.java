package com.beaconstrategists.clientcaseapi.services;

import com.beaconstrategists.clientcaseapi.model.entities.RmaCaseEntity;

import java.util.List;
import java.util.Optional;

public interface RmaCaseService {

    RmaCaseEntity save(RmaCaseEntity rmaCaseEntity);

    List<RmaCaseEntity> findAll();

    Optional<RmaCaseEntity> findById(String id);

    Optional<RmaCaseEntity> findByCaseNumber(String caseNumber);

    boolean isExists(Long id);

    RmaCaseEntity partialUpdate(Long id, RmaCaseEntity tacCaseEntity);

    void delete(Long id);
}
