package com.beaconstrategists.clientcaseapi.repository;

import com.beaconstrategists.clientcaseapi.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {
    Optional<Note> findByIdAndCaseNumber(String id, String caseNumber);

    List<Note> findNotes(String caseNumber, Integer offset, Integer limit,
                         OffsetDateTime createdFrom, OffsetDateTime createdTo,
                         OffsetDateTime createdSince);
}
