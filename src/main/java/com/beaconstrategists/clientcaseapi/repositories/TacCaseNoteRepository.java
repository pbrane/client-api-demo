package com.beaconstrategists.clientcaseapi.repositories;

import com.beaconstrategists.clientcaseapi.model.entities.TacCaseNoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TacCaseNoteRepository extends JpaRepository<TacCaseNoteEntity, Long> {
}
