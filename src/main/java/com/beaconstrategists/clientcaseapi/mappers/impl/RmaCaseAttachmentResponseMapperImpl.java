package com.beaconstrategists.clientcaseapi.mappers.impl;

import com.beaconstrategists.clientcaseapi.controllers.dto.RmaCaseAttachmentResponseDto;
import com.beaconstrategists.clientcaseapi.mappers.RmaCaseAttachmentResponseMapper;
import com.beaconstrategists.clientcaseapi.model.entities.RmaCaseAttachmentEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RmaCaseAttachmentResponseMapperImpl implements RmaCaseAttachmentResponseMapper {

    private final ModelMapper modelMapper;

    public RmaCaseAttachmentResponseMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public RmaCaseAttachmentResponseDto mapTo(RmaCaseAttachmentEntity entity) {
        return modelMapper.map(entity, RmaCaseAttachmentResponseDto.class);
    }
}