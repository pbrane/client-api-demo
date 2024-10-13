package com.beaconstrategists.clientcaseapi.services;

import com.beaconstrategists.clientcaseapi.controllers.dto.TacCaseAttachmentDetailDto;
import com.beaconstrategists.clientcaseapi.controllers.dto.TacCaseAttachmentSummaryDto;
import com.beaconstrategists.clientcaseapi.controllers.dto.TacCaseDto;

import java.util.List;
import java.util.Optional;

public interface TacCaseService {
    // CRUD Operations for TacCase
    TacCaseDto save(TacCaseDto tacCaseDto);
    List<TacCaseDto> findAll();
    Optional<TacCaseDto> findById(Long id);
    Optional<TacCaseDto> findByCaseNumber(String caseNumber);
    boolean isExists(Long id);
    boolean isExists(String caseNumber);
    TacCaseDto partialUpdate(Long id, TacCaseDto tacCaseDto);
    TacCaseDto partialUpdate(String caseNumber, TacCaseDto tacCaseDto);
    void delete(Long id);
    void delete(String caseNumber);

    // Attachment Operations
    TacCaseAttachmentDetailDto addAttachment(Long caseId, TacCaseAttachmentDetailDto tacCaseAttachmentDetailDto);
    TacCaseAttachmentDetailDto updateAttachment(Long caseId, Long attachmentId, TacCaseAttachmentDetailDto tacCaseAttachmentDetailDto);
    Optional<TacCaseAttachmentDetailDto> getAttachment(Long caseId, Long attachmentId);
    List<TacCaseAttachmentSummaryDto> listAttachments(Long caseId);
    void deleteAttachment(Long caseId, Long attachmentId);
}
