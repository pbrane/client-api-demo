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
        return false;
    }

    @Override
    public RmaCaseEntity partialUpdate(Long id, RmaCaseEntity rmaCaseEntity) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }}
