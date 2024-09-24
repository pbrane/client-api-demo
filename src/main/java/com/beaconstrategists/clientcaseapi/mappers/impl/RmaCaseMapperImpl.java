package com.beaconstrategists.clientcaseapi.mappers.impl;

import com.beaconstrategists.clientcaseapi.controllers.dto.RmaCaseDto;
import com.beaconstrategists.clientcaseapi.mappers.Mapper;
import com.beaconstrategists.clientcaseapi.model.entities.RmaCaseEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RmaCaseMapperImpl implements Mapper<RmaCaseEntity, RmaCaseDto> {

    private final ModelMapper modelMapper;

    public RmaCaseMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public RmaCaseDto mapTo(RmaCaseEntity rmaCaseEntity) {
        return modelMapper.map(rmaCaseEntity, RmaCaseDto.class);
    }

    @Override
    public RmaCaseEntity mapFrom(RmaCaseDto rmaCaseDto) {
        return modelMapper.map(rmaCaseDto, RmaCaseEntity.class);
    }
}