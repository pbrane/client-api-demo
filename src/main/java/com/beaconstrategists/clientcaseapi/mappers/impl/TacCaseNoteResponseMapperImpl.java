package com.beaconstrategists.clientcaseapi.mappers.impl;

import com.beaconstrategists.clientcaseapi.controllers.dto.TacCaseNoteResponseDto;
import com.beaconstrategists.clientcaseapi.mappers.TacCaseNoteResponseMapper;
import com.beaconstrategists.clientcaseapi.model.entities.TacCaseNoteEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TacCaseNoteResponseMapperImpl implements TacCaseNoteResponseMapper {

    private final ModelMapper modelMapper;

    public TacCaseNoteResponseMapperImpl(ModelMapper modelMapper) {

        this.modelMapper = modelMapper;
    }

    @Override
    public TacCaseNoteResponseDto mapTo(TacCaseNoteEntity entity) {
        return modelMapper.map(entity, TacCaseNoteResponseDto.class);
    }
}