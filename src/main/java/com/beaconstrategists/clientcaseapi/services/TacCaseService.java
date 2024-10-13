package com.beaconstrategists.clientcaseapi.services;

import com.beaconstrategists.clientcaseapi.controllers.dto.TacCaseAttachmentDownloadDto;
import com.beaconstrategists.clientcaseapi.controllers.dto.TacCaseAttachmentResponseDto;
import com.beaconstrategists.clientcaseapi.controllers.dto.TacCaseAttachmentUploadDto;
import com.beaconstrategists.clientcaseapi.controllers.dto.TacCaseDto;

import java.io.IOException;
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
    TacCaseAttachmentResponseDto addAttachment(Long caseId, TacCaseAttachmentUploadDto uploadDto) throws IOException;
    List<TacCaseAttachmentResponseDto> getAllAttachments(Long caseId);
    TacCaseAttachmentDownloadDto getAttachmentDownload(Long caseId, Long attachmentId);
    void deleteAttachment(Long caseId, Long attachmentId);
    void deleteAllAttachments(Long caseId);
}
