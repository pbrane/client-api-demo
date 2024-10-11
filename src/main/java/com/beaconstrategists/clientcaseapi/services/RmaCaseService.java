package com.beaconstrategists.clientcaseapi.services;

import com.beaconstrategists.clientcaseapi.model.entities.RmaCaseEntity;

import java.util.List;
import java.util.Optional;

public interface RmaCaseService {
    RmaCaseEntity save(RmaCaseEntity rmaCaseEntity);

    List<RmaCaseEntity> findAll();

    Optional<RmaCaseEntity> findById(Long id);

    Optional<RmaCaseEntity> findByCaseNumber(String caseNumber);

    boolean isExists(Long id);

    boolean isExists(String caseNumber);

    RmaCaseEntity partialUpdate(Long id, RmaCaseEntity RmaCaseEntity);

    RmaCaseEntity partialUpdate(String caseNumber, RmaCaseEntity rmaCaseEntity);

    void delete(Long id);

    void delete(String caseNumber);
}
