package com.beaconstrategists.clientcaseapi.repositories;

import com.beaconstrategists.clientcaseapi.model.entities.TacCaseEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TacCaseRepository extends CrudRepository<TacCaseEntity, Long> {

    Optional<TacCaseEntity> findByCaseNumber(String caseNumber);

    boolean existsByCaseNumber(String caseNumber);

    void deleteByCaseNumber(String caseNumber);
}
