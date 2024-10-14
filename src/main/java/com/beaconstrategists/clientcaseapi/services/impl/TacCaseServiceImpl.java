package com.beaconstrategists.clientcaseapi.services.impl;

import com.beaconstrategists.clientcaseapi.controllers.dto.TacCaseAttachmentDownloadDto;
import com.beaconstrategists.clientcaseapi.controllers.dto.TacCaseAttachmentResponseDto;
import com.beaconstrategists.clientcaseapi.controllers.dto.TacCaseAttachmentUploadDto;
import com.beaconstrategists.clientcaseapi.controllers.dto.TacCaseDto;
import com.beaconstrategists.clientcaseapi.exceptions.ResourceNotFoundException;
import com.beaconstrategists.clientcaseapi.mappers.TacCaseAttachmentDownloadMapper;
import com.beaconstrategists.clientcaseapi.mappers.TacCaseAttachmentResponseMapper;
import com.beaconstrategists.clientcaseapi.mappers.impl.TacCaseMapperImpl;
import com.beaconstrategists.clientcaseapi.model.entities.TacCaseAttachmentEntity;
import com.beaconstrategists.clientcaseapi.model.entities.TacCaseEntity;
import com.beaconstrategists.clientcaseapi.repositories.TacCaseAttachmentRepository;
import com.beaconstrategists.clientcaseapi.repositories.TacCaseRepository;
import com.beaconstrategists.clientcaseapi.services.TacCaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TacCaseServiceImpl implements TacCaseService {

    private final TacCaseRepository tacCaseRepository;
    private final TacCaseAttachmentRepository tacCaseAttachmentRepository;
    private final TacCaseMapperImpl tacCaseMapper;
    private final TacCaseAttachmentResponseMapper responseMapper;
    private final TacCaseAttachmentDownloadMapper downloadMapper;


    public TacCaseServiceImpl(TacCaseRepository tacCaseRepository,
                              TacCaseAttachmentRepository tacCaseAttachmentRepository,
                              TacCaseMapperImpl tacCaseMapper,
                              TacCaseAttachmentResponseMapper responseMapper,
                              TacCaseAttachmentDownloadMapper downloadMapper) {
        this.tacCaseRepository = tacCaseRepository;
        this.tacCaseAttachmentRepository = tacCaseAttachmentRepository;
        this.tacCaseMapper = tacCaseMapper;
        this.responseMapper = responseMapper;
        this.downloadMapper = downloadMapper;
    }

    // CRUD Operations for TacCase

    @Override
    @Transactional
    public TacCaseDto save(TacCaseDto tacCaseDto) {
        TacCaseEntity tacCaseEntity = tacCaseMapper.mapFrom(tacCaseDto);
        TacCaseEntity savedEntity = tacCaseRepository.save(tacCaseEntity);
        return tacCaseMapper.mapTo(savedEntity);
    }

    @Override
    @Transactional
    public List<TacCaseDto> findAll() {
        List<TacCaseEntity> tacCases = StreamSupport.stream(tacCaseRepository.findAll().spliterator(), false)
                .toList();
        return tacCases.stream()
                .map(tacCaseMapper::mapTo)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Optional<TacCaseDto> findById(Long id) {
        return tacCaseRepository.findById(id)
                .map(tacCaseMapper::mapTo);
    }

    @Override
    @Transactional
    public Optional<TacCaseDto> findByCaseNumber(String caseNumber) {
        return tacCaseRepository.findByCaseNumber(caseNumber)
                .map(tacCaseMapper::mapTo);
    }

    @Override
    @Transactional
    public boolean isExists(Long id) {
        return tacCaseRepository.existsById(id);
    }

    @Override
    @Transactional
    public boolean isExists(String caseNumber) {
        return tacCaseRepository.existsByCaseNumber(caseNumber);
    }

/*
    @Override
    @Transactional
    public TacCaseDto partialUpdate(Long id, TacCaseDto tacCaseDto) {
        TacCaseEntity existingTacCase = tacCaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TAC Case does not exist with id " + id));

        // Map updated fields from DTO to existing entity
        tacCaseMapper.mapFrom(tacCaseDto, existingTacCase);

        TacCaseEntity updatedTacCase = tacCaseRepository.save(existingTacCase);
        return tacCaseMapper.mapTo(updatedTacCase);
    }
*/
@Override
public TacCaseDto partialUpdate(Long id, TacCaseDto tacCaseDto) {
    return tacCaseRepository.findById(id).map(existingTacCase -> {
        Optional.ofNullable(tacCaseDto.getCaseOwner()).ifPresent(existingTacCase::setCaseOwner);
        Optional.ofNullable(tacCaseDto.getCasePriority()).ifPresent(existingTacCase::setCasePriority);
        Optional.ofNullable(tacCaseDto.getCaseStatus()).ifPresent(existingTacCase::setCaseStatus);
        Optional.ofNullable(tacCaseDto.getAccountNumber()).ifPresent(existingTacCase::setAccountNumber);
        Optional.ofNullable(tacCaseDto.getCaseNumber()).ifPresent(existingTacCase::setCaseNumber);
        Optional.ofNullable(tacCaseDto.getAccountNumber()).ifPresent(existingTacCase::setAccountNumber);
        Optional.ofNullable(tacCaseDto.getBusinessImpact()).ifPresent(existingTacCase::setBusinessImpact);
        Optional.ofNullable(tacCaseDto.getCaseClosedDate()).ifPresent(existingTacCase::setCaseClosedDate);
        Optional.ofNullable(tacCaseDto.getCaseCreatedDate()).ifPresent(existingTacCase::setCaseCreatedDate);
        Optional.ofNullable(tacCaseDto.getCaseNoteCount()).ifPresent(existingTacCase::setCaseNoteCount);
        Optional.ofNullable(tacCaseDto.getCaseSolutionDescription()).ifPresent(existingTacCase::setCaseSolutionDescription);
        Optional.ofNullable(tacCaseDto.getContactEmail()).ifPresent(existingTacCase::setContactEmail);
        Optional.ofNullable(tacCaseDto.getCustomerTrackingNumber()).ifPresent(existingTacCase::setCustomerTrackingNumber);
        Optional.ofNullable(tacCaseDto.getFaultyPartNumber()).ifPresent(existingTacCase::setFaultyPartNumber);
        Optional.ofNullable(tacCaseDto.getFaultySerialNumber()).ifPresent(existingTacCase::setFaultySerialNumber);
        Optional.ofNullable(tacCaseDto.getFirstResponseDate()).ifPresent(existingTacCase::setFirstResponseDate);
        Optional.ofNullable(tacCaseDto.getHref()).ifPresent(existingTacCase::setHref); //fixme
        Optional.ofNullable(tacCaseDto.getCustomerTrackingNumber()).ifPresent(existingTacCase::setCustomerTrackingNumber);
        Optional.ofNullable(tacCaseDto.getInstallationCountry()).ifPresent(existingTacCase::setInstallationCountry);
        Optional.ofNullable(tacCaseDto.getProblemDescription()).ifPresent(existingTacCase::setProblemDescription);
        Optional.ofNullable(tacCaseDto.getCaseNoteCount()).ifPresent(existingTacCase::setCaseNoteCount);
        Optional.ofNullable(tacCaseDto.getProductFirmwareVersion()).ifPresent(existingTacCase::setProductFirmwareVersion);
        Optional.ofNullable(tacCaseDto.getProductSerialNumber()).ifPresent(existingTacCase::setProductSerialNumber);
        Optional.ofNullable(tacCaseDto.getProductName()).ifPresent(existingTacCase::setProductName);
        Optional.ofNullable(tacCaseDto.getProductSoftwareVersion()).ifPresent(existingTacCase::setProductSoftwareVersion);
        Optional.ofNullable(tacCaseDto.getRelatedDispatchCount()).ifPresent(existingTacCase::setRelatedDispatchCount);
        Optional.ofNullable(tacCaseDto.getRelatedRmaCount()).ifPresent(existingTacCase::setRelatedRmaCount);
        Optional.ofNullable(tacCaseDto.getRmaNeeded()).ifPresent(existingTacCase::setRmaNeeded);
        Optional.ofNullable(tacCaseDto.getSubject()).ifPresent(existingTacCase::setSubject);

        TacCaseEntity updatedTacCase = tacCaseRepository.save(existingTacCase);
        return tacCaseMapper.mapTo(updatedTacCase);
    }).orElseThrow(() -> new RuntimeException("TAC Case does not exist"));
}


    @Override
    @Transactional
    public TacCaseDto partialUpdate(String caseNumber, TacCaseDto tacCaseDto) {
        TacCaseEntity existingTacCase = tacCaseRepository.findByCaseNumber(caseNumber)
                .orElseThrow(() -> new ResourceNotFoundException("TAC Case does not exist with case number " + caseNumber));

        // Map updated fields from DTO to existing entity
        tacCaseMapper.mapFrom(tacCaseDto, existingTacCase);

        TacCaseEntity updatedTacCase = tacCaseRepository.save(existingTacCase);
        return tacCaseMapper.mapTo(updatedTacCase);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        TacCaseEntity tacCase = tacCaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TacCase not found with id " + id));
        tacCaseRepository.delete(tacCase);
    }

    @Override
    @Transactional
    public void delete(String caseNumber) {
        TacCaseEntity tacCase = tacCaseRepository.findByCaseNumber(caseNumber)
                .orElseThrow(() -> new ResourceNotFoundException("TAC Case not found with case number " + caseNumber));
        tacCaseRepository.delete(tacCase);
    }

    // Attachment Operations

    @Override
    @Transactional
    public TacCaseAttachmentResponseDto addAttachment(Long caseId, TacCaseAttachmentUploadDto uploadDto) throws IOException {
        TacCaseEntity tacCase = tacCaseRepository.findById(caseId)
                .orElseThrow(() -> new ResourceNotFoundException("RMA Case not found with id " + caseId));

        // Extract file and metadata
        MultipartFile file = uploadDto.getFile();
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File must be provided and not empty.");
        }

        // Optional: Validate file type
        validateFileType(file);

        TacCaseAttachmentEntity attachmentEntity = TacCaseAttachmentEntity.builder()
                .name(Optional.ofNullable(uploadDto.getName()).orElse(file.getOriginalFilename()))
                .mimeType(Optional.ofNullable(uploadDto.getMimeType()).orElse(file.getContentType()))
                .content(file.getBytes())
                .description(uploadDto.getDescription())
                .size((float) file.getSize())
                .tacCase(tacCase)
                .build();

        tacCase.addAttachment(attachmentEntity);
        tacCaseAttachmentRepository.save(attachmentEntity);

        return responseMapper.mapTo(attachmentEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TacCaseAttachmentResponseDto> getAllAttachments(Long caseId) {
        TacCaseEntity rmaCase = tacCaseRepository.findById(caseId)
                .orElseThrow(() -> new ResourceNotFoundException("RMA Case not found with id " + caseId));

        return rmaCase.getAttachments().stream()
                .map(responseMapper::mapTo)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public TacCaseAttachmentDownloadDto getAttachmentDownload(Long caseId, Long attachmentId) {
        TacCaseAttachmentEntity attachment = tacCaseAttachmentRepository.findById(attachmentId)
                .filter(a -> a.getTacCase().getId().equals(caseId))
                .orElseThrow(() -> new ResourceNotFoundException("Attachment not found with id " + attachmentId + " for RMA Case " + caseId));

        return downloadMapper.mapTo(attachment);
    }

    @Override
    @Transactional
    public void deleteAttachment(Long caseId, Long attachmentId) {
        TacCaseAttachmentEntity attachment = tacCaseAttachmentRepository.findById(attachmentId)
                .filter(a -> a.getTacCase().getId().equals(caseId))
                .orElseThrow(() -> new ResourceNotFoundException("Attachment not found with id " + attachmentId + " for RMA Case " + caseId));

        tacCaseAttachmentRepository.delete(attachment);
    }

    @Override
    @Transactional
    public void deleteAllAttachments(Long caseId) {
        TacCaseEntity rmaCase = tacCaseRepository.findById(caseId)
                .orElseThrow(() -> new ResourceNotFoundException("RMA Case not found with id " + caseId));

        List<TacCaseAttachmentEntity> attachments = rmaCase.getAttachments();

        if (!attachments.isEmpty()) {
            tacCaseAttachmentRepository.deleteAll(attachments);
        }
    }

    /**
     * Validates the MIME type of the uploaded file.
     *
     * @param file the uploaded MultipartFile
     */
    private void validateFileType(MultipartFile file) {
        List<String> allowedMimeTypes = Arrays.asList("application/pdf", "image/jpeg", "image/png"); // Extend as needed
        if (!allowedMimeTypes.contains(file.getContentType())) {
            throw new IllegalArgumentException("Unsupported file type: " + file.getContentType());
        }
    }

}
