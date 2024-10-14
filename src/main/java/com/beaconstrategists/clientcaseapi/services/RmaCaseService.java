package com.beaconstrategists.clientcaseapi.services;

import com.beaconstrategists.clientcaseapi.controllers.dto.RmaCaseAttachmentDownloadDto;
import com.beaconstrategists.clientcaseapi.controllers.dto.RmaCaseAttachmentUploadDto;
import com.beaconstrategists.clientcaseapi.controllers.dto.RmaCaseAttachmentResponseDto;
import com.beaconstrategists.clientcaseapi.controllers.dto.RmaCaseDto;

import java.io.IOException;
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
    RmaCaseAttachmentResponseDto addAttachment(Long caseId, RmaCaseAttachmentUploadDto uploadDto) throws IOException;
    List<RmaCaseAttachmentResponseDto> getAllAttachments(Long caseId);
    RmaCaseAttachmentResponseDto getAttachment(Long caseId, Long attachmentId);
    RmaCaseAttachmentDownloadDto getAttachmentDownload(Long caseId, Long attachmentId);
    void deleteAttachment(Long caseId, Long attachmentId);
    void deleteAllAttachments(Long caseId);
}
