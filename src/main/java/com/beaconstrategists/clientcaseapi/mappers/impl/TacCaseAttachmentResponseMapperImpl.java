package com.beaconstrategists.clientcaseapi.mappers.impl;

import com.beaconstrategists.clientcaseapi.controllers.dto.TacCaseAttachmentResponseDto;
import com.beaconstrategists.clientcaseapi.mappers.TacCaseAttachmentResponseMapper;
import com.beaconstrategists.clientcaseapi.model.entities.TacCaseAttachmentEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TacCaseAttachmentResponseMapperImpl implements TacCaseAttachmentResponseMapper {

    private final ModelMapper modelMapper;

    public TacCaseAttachmentResponseMapperImpl(ModelMapper modelMapper) {

        this.modelMapper = modelMapper;
    }

    @Override
    public TacCaseAttachmentResponseDto mapTo(TacCaseAttachmentEntity entity) {
        return modelMapper.map(entity, TacCaseAttachmentResponseDto.class);
    }
}