package com.beaconstrategists.clientcaseapi.mappers.impl;

import com.beaconstrategists.clientcaseapi.controllers.dto.TacCaseNoteDownloadDto;
import com.beaconstrategists.clientcaseapi.mappers.TacCaseNoteDownloadMapper;
import com.beaconstrategists.clientcaseapi.model.entities.TacCaseNoteEntity;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;

@Component
public class TacCaseNoteDownloadMapperImpl implements TacCaseNoteDownloadMapper {

    private final ModelMapper modelMapper;

    public TacCaseNoteDownloadMapperImpl(ModelMapper modelMapper) {

        this.modelMapper = modelMapper;
    }

    @Override
    public TacCaseNoteDownloadDto mapTo(TacCaseNoteEntity entity) {
        TacCaseNoteDownloadDto dto = new TacCaseNoteDownloadDto();
        dto.setId(entity.getId());
        dto.setAuthor(entity.getAuthor());
        dto.setTacCaseId(entity.getTacCase().getId());
        dto.setDate(entity.getDate());
        dto.setText(entity.getText());
        return dto;
    }
}