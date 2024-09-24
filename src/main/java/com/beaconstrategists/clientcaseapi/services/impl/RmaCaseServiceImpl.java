package com.beaconstrategists.clientcaseapi.services.impl;

import com.beaconstrategists.clientcaseapi.model.entities.RmaCaseEntity;
import com.beaconstrategists.clientcaseapi.repositories.RmaCaseRepository;
import com.beaconstrategists.clientcaseapi.services.RmaCaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RmaCaseServiceImpl implements RmaCaseService {

    private final RmaCaseRepository rmaCaseRepository;

    public RmaCaseServiceImpl(RmaCaseRepository rmaCaseRepository) {
        this.rmaCaseRepository = rmaCaseRepository;
    }

    @Override
    public RmaCaseEntity save(RmaCaseEntity rmaCaseEntity) {
        return rmaCaseRepository.save(rmaCaseEntity);
    }

    @Override
    public List<RmaCaseEntity> findAll() {
        return StreamSupport.stream(rmaCaseRepository
                        .findAll()
                        .spliterator(),false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RmaCaseEntity> findById(String id) {
        return Optional.empty();
    }

    @Override
    public Optional<RmaCaseEntity> findByCaseNumber(String caseNumber) {
        return rmaCaseRepository.findByCaseNumber(caseNumber);
    }

    @Override
    public boolean isExists(Long id) {
        return rmaCaseRepository.existsById(id);
    }

    @Override
    public boolean isExists(String caseNumber) {
        return rmaCaseRepository.existsByCaseNumber(caseNumber);
    }

    @Override
    public RmaCaseEntity partialUpdate(Long id, RmaCaseEntity rmaCaseEntity) {
        return rmaCaseRepository.findById(id).map(existingRmaCase -> {
            Optional.ofNullable(rmaCaseEntity.getCaseNumber()).ifPresent(existingRmaCase::setCaseNumber);
            Optional.ofNullable(rmaCaseEntity.getCaseStatus()).ifPresent(existingRmaCase::setCaseStatus);
            Optional.ofNullable(rmaCaseEntity.getCustomerTrackingNumber()).ifPresent(existingRmaCase::setCustomerTrackingNumber);
            Optional.ofNullable(rmaCaseEntity.getContactEmail()).ifPresent(existingRmaCase::setContactEmail);
            Optional.ofNullable(rmaCaseEntity.getCustomerTrackingNumber()).ifPresent(existingRmaCase::setCustomerTrackingNumber);
            Optional.ofNullable(rmaCaseEntity.getFailureAnalysisFinishedDate()).ifPresent(existingRmaCase::setFailureAnalysisFinishedDate);
            Optional.ofNullable(rmaCaseEntity.getFailureAnalysisInProgressDate()).ifPresent(existingRmaCase::setFailureAnalysisInProgressDate);
            Optional.ofNullable(rmaCaseEntity.getFailureAnalysisStartDate()).ifPresent(existingRmaCase::setFailureAnalysisStartDate);
            Optional.ofNullable(rmaCaseEntity.getFaultyPartDeliveredDate()).ifPresent(existingRmaCase::setFaultyPartDeliveredDate);
            Optional.ofNullable(rmaCaseEntity.getFaultyPartNumber()).ifPresent(existingRmaCase::setFaultyPartNumber);
            Optional.ofNullable(rmaCaseEntity.getFaultyPartShippedDate()).ifPresent(existingRmaCase::setFaultyPartShippedDate);
            Optional.ofNullable(rmaCaseEntity.getFaultySerialNumber()).ifPresent(existingRmaCase::setFaultySerialNumber);
            Optional.ofNullable(rmaCaseEntity.getHref()).ifPresent(existingRmaCase::setHref);
            Optional.ofNullable(rmaCaseEntity.getInstallationCountry()).ifPresent(existingRmaCase::setInstallationCountry);
            Optional.ofNullable(rmaCaseEntity.getNewPartDeliveredDate()).ifPresent(existingRmaCase::setNewPartDeliveredDate);
            Optional.ofNullable(rmaCaseEntity.getNewPartSerialNumber()).ifPresent(existingRmaCase::setNewPartSerialNumber);
            Optional.ofNullable(rmaCaseEntity.getNewPartShippedDate()).ifPresent(existingRmaCase::setNewPartShippedDate);
            Optional.ofNullable(rmaCaseEntity.getProblemDescription()).ifPresent(existingRmaCase::setProblemDescription);
//            Optional.ofNullable(rmaCaseEntity.getRelatedTacCaseId()).ifPresent(existingRmaCase::setRelatedTacCaseId);
            Optional.ofNullable(rmaCaseEntity.getRequestType()).ifPresent(existingRmaCase::setRequestType);
            Optional.ofNullable(rmaCaseEntity.getReturnedPartNumber()).ifPresent(existingRmaCase::setReturnedPartNumber);
            Optional.ofNullable(rmaCaseEntity.getReturnedSerialNumber()).ifPresent(existingRmaCase::setReturnedSerialNumber);
            Optional.ofNullable(rmaCaseEntity.getShippedCarrier()).ifPresent(existingRmaCase::setShippedCarrier);
            Optional.ofNullable(rmaCaseEntity.getCaseId()).ifPresent(existingRmaCase::setCaseId); //fixme
            Optional.ofNullable(rmaCaseEntity.getShippedDate()).ifPresent(existingRmaCase::setShippedDate);
            Optional.ofNullable(rmaCaseEntity.getShipToAttention()).ifPresent(existingRmaCase::setShipToAttention);
            Optional.ofNullable(rmaCaseEntity.getShipToCity()).ifPresent(existingRmaCase::setShipToCity);
            Optional.ofNullable(rmaCaseEntity.getShipToContactEmail()).ifPresent(existingRmaCase::setShipToContactEmail);
            Optional.ofNullable(rmaCaseEntity.getShipToCountry()).ifPresent(existingRmaCase::setShipToCountry);
            Optional.ofNullable(rmaCaseEntity.getShipToPhone()).ifPresent(existingRmaCase::setShipToPhone);
            Optional.ofNullable(rmaCaseEntity.getShipToPostalCode()).ifPresent(existingRmaCase::setShipToPostalCode);
            Optional.ofNullable(rmaCaseEntity.getShipToProvince()).ifPresent(existingRmaCase::setShipToProvince);
            Optional.ofNullable(rmaCaseEntity.getShipToStreet1()).ifPresent(existingRmaCase::setShipToStreet1);
            Optional.ofNullable(rmaCaseEntity.getTacCase()).ifPresent(existingRmaCase::setTacCase); //fixme
            Optional.ofNullable(rmaCaseEntity.getVendorRmaNumber()).ifPresent(existingRmaCase::setVendorRmaNumber);
            return rmaCaseRepository.save(existingRmaCase);
        }).orElseThrow(() -> new RuntimeException("RMA Case does not exist"));
    }

    @Override
    public RmaCaseEntity partialUpdate(String caseNumber, RmaCaseEntity rmaCaseEntity) {
        return rmaCaseRepository.findByCaseNumber(caseNumber).map(existingRmaCase -> {
            Optional.ofNullable(rmaCaseEntity.getCaseNumber()).ifPresent(existingRmaCase::setCaseNumber);
            Optional.ofNullable(rmaCaseEntity.getCaseStatus()).ifPresent(existingRmaCase::setCaseStatus);
            Optional.ofNullable(rmaCaseEntity.getCustomerTrackingNumber()).ifPresent(existingRmaCase::setCustomerTrackingNumber);
            Optional.ofNullable(rmaCaseEntity.getContactEmail()).ifPresent(existingRmaCase::setContactEmail);
            Optional.ofNullable(rmaCaseEntity.getCustomerTrackingNumber()).ifPresent(existingRmaCase::setCustomerTrackingNumber);
            Optional.ofNullable(rmaCaseEntity.getFailureAnalysisFinishedDate()).ifPresent(existingRmaCase::setFailureAnalysisFinishedDate);
            Optional.ofNullable(rmaCaseEntity.getFailureAnalysisInProgressDate()).ifPresent(existingRmaCase::setFailureAnalysisInProgressDate);
            Optional.ofNullable(rmaCaseEntity.getFailureAnalysisStartDate()).ifPresent(existingRmaCase::setFailureAnalysisStartDate);
            Optional.ofNullable(rmaCaseEntity.getFaultyPartDeliveredDate()).ifPresent(existingRmaCase::setFaultyPartDeliveredDate);
            Optional.ofNullable(rmaCaseEntity.getFaultyPartNumber()).ifPresent(existingRmaCase::setFaultyPartNumber);
            Optional.ofNullable(rmaCaseEntity.getFaultyPartShippedDate()).ifPresent(existingRmaCase::setFaultyPartShippedDate);
            Optional.ofNullable(rmaCaseEntity.getFaultySerialNumber()).ifPresent(existingRmaCase::setFaultySerialNumber);
            Optional.ofNullable(rmaCaseEntity.getHref()).ifPresent(existingRmaCase::setHref);
            Optional.ofNullable(rmaCaseEntity.getInstallationCountry()).ifPresent(existingRmaCase::setInstallationCountry);
            Optional.ofNullable(rmaCaseEntity.getNewPartDeliveredDate()).ifPresent(existingRmaCase::setNewPartDeliveredDate);
            Optional.ofNullable(rmaCaseEntity.getNewPartSerialNumber()).ifPresent(existingRmaCase::setNewPartSerialNumber);
            Optional.ofNullable(rmaCaseEntity.getNewPartShippedDate()).ifPresent(existingRmaCase::setNewPartShippedDate);
            Optional.ofNullable(rmaCaseEntity.getProblemDescription()).ifPresent(existingRmaCase::setProblemDescription);
//            Optional.ofNullable(rmaCaseEntity.getRelatedTacCaseId()).ifPresent(existingRmaCase::setRelatedTacCaseId);
            Optional.ofNullable(rmaCaseEntity.getRequestType()).ifPresent(existingRmaCase::setRequestType);
            Optional.ofNullable(rmaCaseEntity.getReturnedPartNumber()).ifPresent(existingRmaCase::setReturnedPartNumber);
            Optional.ofNullable(rmaCaseEntity.getReturnedSerialNumber()).ifPresent(existingRmaCase::setReturnedSerialNumber);
            Optional.ofNullable(rmaCaseEntity.getShippedCarrier()).ifPresent(existingRmaCase::setShippedCarrier);
            Optional.ofNullable(rmaCaseEntity.getCaseId()).ifPresent(existingRmaCase::setCaseId); //fixme
            Optional.ofNullable(rmaCaseEntity.getShippedDate()).ifPresent(existingRmaCase::setShippedDate);
            Optional.ofNullable(rmaCaseEntity.getShipToAttention()).ifPresent(existingRmaCase::setShipToAttention);
            Optional.ofNullable(rmaCaseEntity.getShipToCity()).ifPresent(existingRmaCase::setShipToCity);
            Optional.ofNullable(rmaCaseEntity.getShipToContactEmail()).ifPresent(existingRmaCase::setShipToContactEmail);
            Optional.ofNullable(rmaCaseEntity.getShipToCountry()).ifPresent(existingRmaCase::setShipToCountry);
            Optional.ofNullable(rmaCaseEntity.getShipToPhone()).ifPresent(existingRmaCase::setShipToPhone);
            Optional.ofNullable(rmaCaseEntity.getShipToPostalCode()).ifPresent(existingRmaCase::setShipToPostalCode);
            Optional.ofNullable(rmaCaseEntity.getShipToProvince()).ifPresent(existingRmaCase::setShipToProvince);
            Optional.ofNullable(rmaCaseEntity.getShipToStreet1()).ifPresent(existingRmaCase::setShipToStreet1);
            Optional.ofNullable(rmaCaseEntity.getTacCase()).ifPresent(existingRmaCase::setTacCase); //fixme
            Optional.ofNullable(rmaCaseEntity.getVendorRmaNumber()).ifPresent(existingRmaCase::setVendorRmaNumber);
            return rmaCaseRepository.save(existingRmaCase);
        }).orElseThrow(() -> new RuntimeException("RMA Case does not exist"));
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void delete(String caseNumber) {

    }
}
