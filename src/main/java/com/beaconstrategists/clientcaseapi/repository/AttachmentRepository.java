package com.beaconstrategists.clientcaseapi.repository;

import com.beaconstrategists.clientcaseapi.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    Optional<Attachment> findByIdAndCaseNumber(String id, String caseNumber);

    List<Attachment> findByCaseNumber(String caseNumber, Integer offset, Integer limit);
}
