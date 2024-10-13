package com.beaconstrategists.clientcaseapi.services;

import com.beaconstrategists.clientcaseapi.controllers.dto.RmaCaseAttachmentDetailDto;
import com.beaconstrategists.clientcaseapi.controllers.dto.RmaCaseAttachmentSummaryDto;
import com.beaconstrategists.clientcaseapi.controllers.dto.RmaCaseDto;

import java.util.List;
import java.util.Optional;

public interface RmaCaseService {
    // CRUD Operations for TacCase
    RmaCaseDto save(RmaCaseDto rmaCaseDto);
    List<RmaCaseDto> findAll();
    Optional<RmaCaseDto> findById(Long id);
    Optional<RmaCaseDto> findByCaseNumber(String caseNumber);
    boolean isExists(Long id);
    boolean isExists(String caseNumber);
    RmaCaseDto partialUpdate(Long id, RmaCaseDto tacCaseDto);
    RmaCaseDto partialUpdate(String caseNumber, RmaCaseDto tacCaseDto);
    void delete(Long id);
    void delete(String caseNumber);

    // Attachment Operations
    RmaCaseAttachmentDetailDto addAttachment(Long caseId, RmaCaseAttachmentDetailDto rmaCaseAttachmentDetailDto);
    RmaCaseAttachmentDetailDto updateAttachment(Long caseId, Long attachmentId, RmaCaseAttachmentDetailDto rmaCaseAttachmentDetailDto);
    Optional<RmaCaseAttachmentDetailDto> getAttachment(Long caseId, Long attachmentId);
    List<RmaCaseAttachmentSummaryDto> listAttachments(Long caseId);
    void deleteAttachment(Long caseId, Long attachmentId);
}
