package com.beaconstrategists.clientcaseapi.mappers.impl;

import com.beaconstrategists.clientcaseapi.controllers.dto.TacCaseAttachmentDownloadDto;
import com.beaconstrategists.clientcaseapi.mappers.TacCaseAttachmentDownloadMapper;
import com.beaconstrategists.clientcaseapi.model.entities.TacCaseAttachmentEntity;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;

@Component
public class TacCaseAttachmentDownloadMapperImpl implements TacCaseAttachmentDownloadMapper {

    private final ModelMapper modelMapper;

    public TacCaseAttachmentDownloadMapperImpl(ModelMapper modelMapper) {

        this.modelMapper = modelMapper;
    }

    @Override
    public TacCaseAttachmentDownloadDto mapTo(TacCaseAttachmentEntity entity) {
        TacCaseAttachmentDownloadDto dto = new TacCaseAttachmentDownloadDto();
        dto.setName(entity.getName());
        dto.setMimeType(entity.getMimeType());
        // Convert byte[] to ByteArrayResource or use a streaming approach
        dto.setResource(new ByteArrayResource(entity.getContent()));
        return dto;
    }
}