package com.beaconstrategists.clientcaseapi.services;

import com.beaconstrategists.clientcaseapi.model.entities.TacCaseEntity;

import java.util.List;
import java.util.Optional;

public interface TacCaseService {

    TacCaseEntity save(TacCaseEntity tacCaseEntity);

    List<TacCaseEntity> findAll();

    Optional<TacCaseEntity> findById(Long id);

    Optional<TacCaseEntity> findByCaseNumber(String caseNumber);

    boolean isExists(Long id);

    boolean isExists(String caseNumber);

    TacCaseEntity partialUpdate(Long id, TacCaseEntity tacCaseEntity);

    TacCaseEntity partialUpdate(String caseNumber, TacCaseEntity tacCaseEntity);

    void delete(Long id);

    void delete(String caseNumber);
}
