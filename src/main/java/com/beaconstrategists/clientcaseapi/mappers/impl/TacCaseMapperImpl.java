package com.beaconstrategists.clientcaseapi.mappers.impl;

import com.beaconstrategists.clientcaseapi.mappers.Mapper;
import com.beaconstrategists.clientcaseapi.model.entities.TacCaseEntity;
import com.beaconstrategists.clientcaseapi.controllers.dto.TacCaseDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TacCaseMapperImpl implements Mapper<TacCaseEntity, TacCaseDto> {

    private final ModelMapper modelMapper;

    public TacCaseMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public TacCaseDto mapTo(TacCaseEntity tacCaseEntity) {
        return modelMapper.map(tacCaseEntity, TacCaseDto.class);
    }

    @Override
    public TacCaseEntity mapFrom(TacCaseDto tacCaseDto) {
        return modelMapper.map(tacCaseDto, TacCaseEntity.class);
    }
}