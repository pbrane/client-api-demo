package com.beaconstrategists.clientcaseapi.services.impl;

import com.beaconstrategists.clientcaseapi.model.entities.TacCaseEntity;
import com.beaconstrategists.clientcaseapi.repositories.TacCaseRepository;
import com.beaconstrategists.clientcaseapi.services.TacCaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TacCaseServiceImpl implements TacCaseService {

    private final TacCaseRepository tacCaseRepository;

    public TacCaseServiceImpl(TacCaseRepository tacCaseRepository) {
        this.tacCaseRepository = tacCaseRepository;
    }

    @Override
    public TacCaseEntity save(TacCaseEntity tacCaseEntity) {
        return tacCaseRepository.save(tacCaseEntity);
    }

    @Override
    public List<TacCaseEntity> findAll() {
        return StreamSupport.stream(tacCaseRepository
                .findAll()
                .spliterator(),false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TacCaseEntity> findById(Long id) {
        return tacCaseRepository.findById(id);
    }

    @Override
    public Optional<TacCaseEntity> findByCaseNumber(String caseNumber) {
        return tacCaseRepository.findByCaseNumber(caseNumber);
    }

    @Override
    public boolean isExists(Long id) {
        return tacCaseRepository.existsById(id);
    }

    @Override
    public boolean isExists(String caseNumber) {
        return tacCaseRepository.existsByCaseNumber(caseNumber);
    }

    @Override
    public TacCaseEntity partialUpdate(Long id, TacCaseEntity tacCaseEntity) {
        return tacCaseRepository.findById(id).map(existingTacCase -> {
            Optional.ofNullable(tacCaseEntity.getCaseOwner()).ifPresent(existingTacCase::setCaseOwner);
            Optional.ofNullable(tacCaseEntity.getCasePriority()).ifPresent(existingTacCase::setCasePriority);
            Optional.ofNullable(tacCaseEntity.getCaseStatus()).ifPresent(existingTacCase::setCaseStatus);
            Optional.ofNullable(tacCaseEntity.getAccountNumber()).ifPresent(existingTacCase::setAccountNumber);
            Optional.ofNullable(tacCaseEntity.getCaseNumber()).ifPresent(existingTacCase::setCaseNumber);
            Optional.ofNullable(tacCaseEntity.getAccountNumber()).ifPresent(existingTacCase::setAccountNumber);
            Optional.ofNullable(tacCaseEntity.getBusinessImpact()).ifPresent(existingTacCase::setBusinessImpact);
            Optional.ofNullable(tacCaseEntity.getCaseClosedDate()).ifPresent(existingTacCase::setCaseClosedDate);
            Optional.ofNullable(tacCaseEntity.getCaseCreatedDate()).ifPresent(existingTacCase::setCaseCreatedDate);
            Optional.ofNullable(tacCaseEntity.getCaseNoteCount()).ifPresent(existingTacCase::setCaseNoteCount);
            Optional.ofNullable(tacCaseEntity.getCaseSolutionDescription()).ifPresent(existingTacCase::setCaseSolutionDescription);
            Optional.ofNullable(tacCaseEntity.getContactEmail()).ifPresent(existingTacCase::setContactEmail);
            Optional.ofNullable(tacCaseEntity.getCustomerTrackingNumber()).ifPresent(existingTacCase::setCustomerTrackingNumber);
            Optional.ofNullable(tacCaseEntity.getFaultyPartNumber()).ifPresent(existingTacCase::setFaultyPartNumber);
            Optional.ofNullable(tacCaseEntity.getFaultySerialNumber()).ifPresent(existingTacCase::setFaultySerialNumber);
            Optional.ofNullable(tacCaseEntity.getFirstResponseDate()).ifPresent(existingTacCase::setFirstResponseDate);
            Optional.ofNullable(tacCaseEntity.getHref()).ifPresent(existingTacCase::setHref); //fixme
            Optional.ofNullable(tacCaseEntity.getCustomerTrackingNumber()).ifPresent(existingTacCase::setCustomerTrackingNumber);
            Optional.ofNullable(tacCaseEntity.getInstallationCountry()).ifPresent(existingTacCase::setInstallationCountry);
            Optional.ofNullable(tacCaseEntity.getProblemDescription()).ifPresent(existingTacCase::setProblemDescription);
//            Optional.ofNullable(tacCaseEntity.getNotes()).ifPresent(existingTacCase::setNotes); //fixme
            Optional.ofNullable(tacCaseEntity.getCaseNoteCount()).ifPresent(existingTacCase::setCaseNoteCount);
            Optional.ofNullable(tacCaseEntity.getProductFirmwareVersion()).ifPresent(existingTacCase::setProductFirmwareVersion);
            Optional.ofNullable(tacCaseEntity.getProductSerialNumber()).ifPresent(existingTacCase::setProductSerialNumber);
            Optional.ofNullable(tacCaseEntity.getProductName()).ifPresent(existingTacCase::setProductName);
            Optional.ofNullable(tacCaseEntity.getProductSoftwareVersion()).ifPresent(existingTacCase::setProductSoftwareVersion);
            Optional.ofNullable(tacCaseEntity.getRelatedDispatchCount()).ifPresent(existingTacCase::setRelatedDispatchCount);
            Optional.ofNullable(tacCaseEntity.getRelatedRmaCount()).ifPresent(existingTacCase::setRelatedRmaCount);
            Optional.ofNullable(tacCaseEntity.getRmaNeeded()).ifPresent(existingTacCase::setRmaNeeded);
            Optional.ofNullable(tacCaseEntity.getSubject()).ifPresent(existingTacCase::setSubject);

            return tacCaseRepository.save(existingTacCase);
        }).orElseThrow(() -> new RuntimeException("TAC Case does not exist"));
    }

    @Override
    public TacCaseEntity partialUpdate(String caseNumber, TacCaseEntity tacCaseEntity) {
        return tacCaseRepository.findByCaseNumber(caseNumber).map(existingTacCase -> {
            Optional.ofNullable(tacCaseEntity.getCaseOwner()).ifPresent(existingTacCase::setCaseOwner);
            Optional.ofNullable(tacCaseEntity.getCasePriority()).ifPresent(existingTacCase::setCasePriority);
            Optional.ofNullable(tacCaseEntity.getCaseStatus()).ifPresent(existingTacCase::setCaseStatus);
            Optional.ofNullable(tacCaseEntity.getAccountNumber()).ifPresent(existingTacCase::setAccountNumber);
            Optional.ofNullable(tacCaseEntity.getCaseNumber()).ifPresent(existingTacCase::setCaseNumber);
            Optional.ofNullable(tacCaseEntity.getAccountNumber()).ifPresent(existingTacCase::setAccountNumber);
            Optional.ofNullable(tacCaseEntity.getBusinessImpact()).ifPresent(existingTacCase::setBusinessImpact);
            Optional.ofNullable(tacCaseEntity.getCaseClosedDate()).ifPresent(existingTacCase::setCaseClosedDate);
            Optional.ofNullable(tacCaseEntity.getCaseCreatedDate()).ifPresent(existingTacCase::setCaseCreatedDate);
            Optional.ofNullable(tacCaseEntity.getCaseNoteCount()).ifPresent(existingTacCase::setCaseNoteCount);
            Optional.ofNullable(tacCaseEntity.getCaseSolutionDescription()).ifPresent(existingTacCase::setCaseSolutionDescription);
            Optional.ofNullable(tacCaseEntity.getContactEmail()).ifPresent(existingTacCase::setContactEmail);
            Optional.ofNullable(tacCaseEntity.getCustomerTrackingNumber()).ifPresent(existingTacCase::setCustomerTrackingNumber);
            Optional.ofNullable(tacCaseEntity.getFaultyPartNumber()).ifPresent(existingTacCase::setFaultyPartNumber);
            Optional.ofNullable(tacCaseEntity.getFaultySerialNumber()).ifPresent(existingTacCase::setFaultySerialNumber);
            Optional.ofNullable(tacCaseEntity.getFirstResponseDate()).ifPresent(existingTacCase::setFirstResponseDate);
            Optional.ofNullable(tacCaseEntity.getHref()).ifPresent(existingTacCase::setHref); //fixme
            Optional.ofNullable(tacCaseEntity.getCustomerTrackingNumber()).ifPresent(existingTacCase::setCustomerTrackingNumber);
            Optional.ofNullable(tacCaseEntity.getInstallationCountry()).ifPresent(existingTacCase::setInstallationCountry);
            Optional.ofNullable(tacCaseEntity.getProblemDescription()).ifPresent(existingTacCase::setProblemDescription);
//            Optional.ofNullable(tacCaseEntity.getNotes()).ifPresent(existingTacCase::setNotes); //fixme
            Optional.ofNullable(tacCaseEntity.getCaseNoteCount()).ifPresent(existingTacCase::setCaseNoteCount);
            Optional.ofNullable(tacCaseEntity.getProductFirmwareVersion()).ifPresent(existingTacCase::setProductFirmwareVersion);
            Optional.ofNullable(tacCaseEntity.getProductSerialNumber()).ifPresent(existingTacCase::setProductSerialNumber);
            Optional.ofNullable(tacCaseEntity.getProductName()).ifPresent(existingTacCase::setProductName);
            Optional.ofNullable(tacCaseEntity.getProductSoftwareVersion()).ifPresent(existingTacCase::setProductSoftwareVersion);
            Optional.ofNullable(tacCaseEntity.getRelatedDispatchCount()).ifPresent(existingTacCase::setRelatedDispatchCount);
            Optional.ofNullable(tacCaseEntity.getRelatedRmaCount()).ifPresent(existingTacCase::setRelatedRmaCount);
            Optional.ofNullable(tacCaseEntity.getRmaNeeded()).ifPresent(existingTacCase::setRmaNeeded);
            Optional.ofNullable(tacCaseEntity.getSubject()).ifPresent(existingTacCase::setSubject);

            return tacCaseRepository.save(existingTacCase);
        }).orElseThrow(() -> new RuntimeException("TAC Case does not exist"));
    }

    @Override
    public void delete(Long id) {
        tacCaseRepository.deleteById(id);
    }

    @Override
    public void delete(String caseNumber) {
        tacCaseRepository.deleteByCaseNumber(caseNumber);
    }
}