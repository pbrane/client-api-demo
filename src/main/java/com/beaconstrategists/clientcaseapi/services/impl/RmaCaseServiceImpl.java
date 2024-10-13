package com.beaconstrategists.clientcaseapi.services.impl;

import com.beaconstrategists.clientcaseapi.controllers.dto.RmaCaseAttachmentDetailDto;
import com.beaconstrategists.clientcaseapi.controllers.dto.RmaCaseAttachmentSummaryDto;
import com.beaconstrategists.clientcaseapi.controllers.dto.RmaCaseDto;
import com.beaconstrategists.clientcaseapi.exceptions.ResourceNotFoundException;
import com.beaconstrategists.clientcaseapi.mappers.impl.RmaCaseAttachmentDetailMapperImpl;
import com.beaconstrategists.clientcaseapi.mappers.impl.RmaCaseAttachmentSummaryMapperImpl;
import com.beaconstrategists.clientcaseapi.mappers.impl.RmaCaseMapperImpl;
import com.beaconstrategists.clientcaseapi.model.entities.RmaCaseAttachmentEntity;
import com.beaconstrategists.clientcaseapi.model.entities.RmaCaseEntity;
import com.beaconstrategists.clientcaseapi.repositories.RmaCaseAttachmentRepository;
import com.beaconstrategists.clientcaseapi.repositories.RmaCaseRepository;
import com.beaconstrategists.clientcaseapi.services.RmaCaseService;
//import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RmaCaseServiceImpl implements RmaCaseService {

    private final RmaCaseRepository rmaCaseRepository;
    private final RmaCaseAttachmentRepository rmaCaseAttachmentRepository;
    private final RmaCaseAttachmentDetailMapperImpl attachmentDetailMapper;
    private final RmaCaseAttachmentSummaryMapperImpl attachmentSummaryMapper;
    private final RmaCaseMapperImpl rmaCaseMapper;

    public RmaCaseServiceImpl(RmaCaseRepository rmaCaseRepository,
                              RmaCaseAttachmentRepository rmaCaseAttachmentRepository,
                              RmaCaseAttachmentDetailMapperImpl attachmentDetailMapper,
                              RmaCaseAttachmentSummaryMapperImpl attachmentSummaryMapper,
                              RmaCaseMapperImpl rmaCaseMapper) {
        this.rmaCaseRepository = rmaCaseRepository;
        this.rmaCaseAttachmentRepository = rmaCaseAttachmentRepository;
        this.attachmentDetailMapper = attachmentDetailMapper;
        this.attachmentSummaryMapper = attachmentSummaryMapper;
        this.rmaCaseMapper = rmaCaseMapper;
    }

    // CRUD Operations for RmaCase

    @Override
    @Transactional
    public RmaCaseDto save(RmaCaseDto rmaCaseDto) {
        RmaCaseEntity rmaCaseEntity = rmaCaseMapper.mapFrom(rmaCaseDto);
        RmaCaseEntity savedEntity = rmaCaseRepository.save(rmaCaseEntity);
        return rmaCaseMapper.mapTo(savedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RmaCaseDto> findAll() {
        List<RmaCaseEntity> rmaCases = StreamSupport.stream(rmaCaseRepository.findAll().spliterator(), false)
                .toList();
        return rmaCases.stream()
                .map(rmaCaseMapper::mapTo)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RmaCaseDto> findById(Long id) {
        return rmaCaseRepository.findById(id)
                .map(rmaCaseMapper::mapTo);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RmaCaseDto> findByCaseNumber(String caseNumber) {
        return rmaCaseRepository.findByCaseNumber(caseNumber)
                .map(rmaCaseMapper::mapTo);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExists(Long id) {
        return rmaCaseRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExists(String caseNumber) {
        return rmaCaseRepository.existsByCaseNumber(caseNumber);
    }

    @Override
    @Transactional
    public RmaCaseDto partialUpdate(String caseNumber, RmaCaseDto rmaCaseDto) {
        RmaCaseEntity existingRmaCase = rmaCaseRepository.findByCaseNumber(caseNumber)
                .orElseThrow(() -> new ResourceNotFoundException("TAC Case does not exist with case number " + caseNumber));

        // Map updated fields from DTO to existing entity
        rmaCaseMapper.mapFrom(rmaCaseDto, existingRmaCase);

        RmaCaseEntity updatedRmaCase = rmaCaseRepository.save(existingRmaCase);
        return rmaCaseMapper.mapTo(updatedRmaCase);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        RmaCaseEntity rmaCase = rmaCaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RmaCase not found with id " + id));
        rmaCaseRepository.delete(rmaCase);
    }

    @Override
    @Transactional
    public void delete(String caseNumber) {
        RmaCaseEntity rmaCase = rmaCaseRepository.findByCaseNumber(caseNumber)
                .orElseThrow(() -> new ResourceNotFoundException("TAC Case not found with case number " + caseNumber));
        rmaCaseRepository.delete(rmaCase);
    }

    // Attachment Operations

    @Override
    @Transactional
    public RmaCaseAttachmentDetailDto addAttachment(Long caseId, RmaCaseAttachmentDetailDto rmaCaseAttachmentDetailDto) {
        RmaCaseEntity rmaCase = rmaCaseRepository.findById(caseId)
                .orElseThrow(() -> new ResourceNotFoundException("TAC Case not found with id " + caseId));

        RmaCaseAttachmentEntity attachmentEntity = attachmentDetailMapper.mapFrom(rmaCaseAttachmentDetailDto);
        rmaCase.addAttachment(attachmentEntity);

        RmaCaseAttachmentEntity savedAttachment = rmaCaseAttachmentRepository.save(attachmentEntity);
        return attachmentDetailMapper.mapTo(savedAttachment);
    }

    @Override
    @Transactional
    public RmaCaseDto partialUpdate(Long id, RmaCaseDto rmaCaseDto) {
        RmaCaseEntity existingRmaCase = rmaCaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TAC Case does not exist with id " + id));

        // Map updated fields from DTO to existing entity
        rmaCaseMapper.mapFrom(rmaCaseDto, existingRmaCase);

        RmaCaseEntity updatedRmaCase = rmaCaseRepository.save(existingRmaCase);
        return rmaCaseMapper.mapTo(updatedRmaCase);
    }

    @Override
    @Transactional
    public RmaCaseAttachmentDetailDto updateAttachment(Long caseId, Long attachmentId, RmaCaseAttachmentDetailDto rmaCaseAttachmentDetailDto) {
        RmaCaseAttachmentEntity existingAttachment = rmaCaseAttachmentRepository.findByIdAndRmaCaseId(attachmentId, caseId)
                .orElseThrow(() -> new ResourceNotFoundException("Attachment not found with id " + attachmentId + " for RmaCase " + caseId));

        // Map updated fields from DTO to existing entity
        attachmentDetailMapper.mapFrom(rmaCaseAttachmentDetailDto, existingAttachment);

        RmaCaseAttachmentEntity updatedAttachment = rmaCaseAttachmentRepository.save(existingAttachment);
        return attachmentDetailMapper.mapTo(updatedAttachment);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RmaCaseAttachmentDetailDto> getAttachment(Long caseId, Long attachmentId) {
        RmaCaseAttachmentEntity attachmentEntity = rmaCaseAttachmentRepository.findByIdAndRmaCaseId(attachmentId, caseId)
                .orElseThrow(() -> new ResourceNotFoundException("Attachment not found with id " + attachmentId + " for case " + caseId));

        return Optional.ofNullable(attachmentDetailMapper.mapTo(attachmentEntity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RmaCaseAttachmentSummaryDto> listAttachments(Long caseId) {
        // Check if the RmaCase exists
        boolean caseExists = rmaCaseRepository.existsById(caseId);
        if (!caseExists) {
            throw new ResourceNotFoundException("RmaCase not found with id " + caseId);
        }

        // Retrieve attachments
        List<RmaCaseAttachmentEntity> attachments = rmaCaseAttachmentRepository.findAllByRmaCaseId(caseId);

        return attachments.stream()
                .map(attachmentSummaryMapper::mapTo)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteAttachment(Long caseId, Long attachmentId) {
        RmaCaseAttachmentEntity existingAttachment = rmaCaseAttachmentRepository.findByIdAndRmaCaseId(attachmentId, caseId)
                .orElseThrow(() -> new ResourceNotFoundException("Attachment not found with id " + attachmentId + " for RmaCase " + caseId));

        rmaCaseAttachmentRepository.delete(existingAttachment);
    }
}
