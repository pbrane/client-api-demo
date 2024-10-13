package com.beaconstrategists.clientcaseapi.services.impl;

import com.beaconstrategists.clientcaseapi.controllers.dto.TacCaseAttachmentDetailDto;
import com.beaconstrategists.clientcaseapi.controllers.dto.TacCaseAttachmentSummaryDto;
import com.beaconstrategists.clientcaseapi.controllers.dto.TacCaseDto;
import com.beaconstrategists.clientcaseapi.exceptions.ResourceNotFoundException;
import com.beaconstrategists.clientcaseapi.mappers.impl.TacCaseAttachmentDetailMapperImpl;
import com.beaconstrategists.clientcaseapi.mappers.impl.TacCaseAttachmentSummaryMapperImpl;
import com.beaconstrategists.clientcaseapi.mappers.impl.TacCaseMapperImpl;
import com.beaconstrategists.clientcaseapi.model.entities.RmaCaseEntity;
import com.beaconstrategists.clientcaseapi.model.entities.TacCaseAttachmentEntity;
import com.beaconstrategists.clientcaseapi.model.entities.TacCaseEntity;
import com.beaconstrategists.clientcaseapi.repositories.TacCaseAttachmentRepository;
import com.beaconstrategists.clientcaseapi.repositories.TacCaseRepository;
import com.beaconstrategists.clientcaseapi.services.TacCaseService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TacCaseServiceImpl implements TacCaseService {

    private final TacCaseRepository tacCaseRepository;
    private final TacCaseAttachmentRepository tacCaseAttachmentRepository;
    private final TacCaseAttachmentDetailMapperImpl attachmentDetailMapper;
    private final TacCaseAttachmentSummaryMapperImpl attachmentSummaryMapper;
    private final TacCaseMapperImpl tacCaseMapper;

    public TacCaseServiceImpl(TacCaseRepository tacCaseRepository,
                              TacCaseAttachmentRepository tacCaseAttachmentRepository,
                              TacCaseAttachmentDetailMapperImpl attachmentDetailMapper,
                              TacCaseAttachmentSummaryMapperImpl attachmentSummaryMapper,
                              TacCaseMapperImpl tacCaseMapper) {
        this.tacCaseRepository = tacCaseRepository;
        this.tacCaseAttachmentRepository = tacCaseAttachmentRepository;
        this.attachmentDetailMapper = attachmentDetailMapper;
        this.attachmentSummaryMapper = attachmentSummaryMapper;
        this.tacCaseMapper = tacCaseMapper;
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
    public TacCaseAttachmentDetailDto addAttachment(Long caseId, TacCaseAttachmentDetailDto tacCaseAttachmentDetailDto) {
        TacCaseEntity tacCase = tacCaseRepository.findById(caseId)
                .orElseThrow(() -> new ResourceNotFoundException("TAC Case not found with id " + caseId));

        TacCaseAttachmentEntity attachmentEntity = attachmentDetailMapper.mapFrom(tacCaseAttachmentDetailDto);
        tacCase.addAttachment(attachmentEntity);

        TacCaseAttachmentEntity savedAttachment = tacCaseAttachmentRepository.save(attachmentEntity);
        return attachmentDetailMapper.mapTo(savedAttachment);
    }

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

    @Override
    @Transactional
    public TacCaseAttachmentDetailDto updateAttachment(Long caseId, Long attachmentId, TacCaseAttachmentDetailDto tacCaseAttachmentDetailDto) {
        TacCaseAttachmentEntity existingAttachment = tacCaseAttachmentRepository.findByIdAndTacCaseId(attachmentId, caseId)
                .orElseThrow(() -> new ResourceNotFoundException("Attachment not found with id " + attachmentId + " for TacCase " + caseId));

        // Map updated fields from DTO to existing entity
        attachmentDetailMapper.mapFrom(tacCaseAttachmentDetailDto, existingAttachment);

        TacCaseAttachmentEntity updatedAttachment = tacCaseAttachmentRepository.save(existingAttachment);
        return attachmentDetailMapper.mapTo(updatedAttachment);
    }

    @Override
    @Transactional
    public Optional<TacCaseAttachmentDetailDto> getAttachment(Long caseId, Long attachmentId) {
        TacCaseAttachmentEntity attachmentEntity = tacCaseAttachmentRepository.findByIdAndTacCaseId(attachmentId, caseId)
                .orElseThrow(() -> new ResourceNotFoundException("Attachment not found with id " + attachmentId + " for case " + caseId));

        return Optional.ofNullable(attachmentDetailMapper.mapTo(attachmentEntity));
    }

    @Override
    @Transactional
    public List<TacCaseAttachmentSummaryDto> listAttachments(Long caseId) {
        // Check if the TacCase exists
        boolean caseExists = tacCaseRepository.existsById(caseId);
        if (!caseExists) {
            throw new ResourceNotFoundException("TacCase not found with id " + caseId);
        }

        // Retrieve attachments
        List<TacCaseAttachmentEntity> attachments = tacCaseAttachmentRepository.findAllByTacCaseId(caseId);

        return attachments.stream()
                .map(attachmentSummaryMapper::mapTo)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteAttachment(Long caseId, Long attachmentId) {
        TacCaseAttachmentEntity existingAttachment = tacCaseAttachmentRepository.findByIdAndTacCaseId(attachmentId, caseId)
                .orElseThrow(() -> new ResourceNotFoundException("Attachment not found with id " + attachmentId + " for TacCase " + caseId));

        tacCaseAttachmentRepository.delete(existingAttachment);
    }
}
