package com.beaconstrategists.clientcaseapi.mappers.impl;

import com.beaconstrategists.clientcaseapi.controllers.dto.TacCaseAttachmentUploadDto;
import com.beaconstrategists.clientcaseapi.mappers.TacCaseAttachmentUploadMapper;
import com.beaconstrategists.clientcaseapi.model.entities.TacCaseAttachmentEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TacCaseAttachmentUploadMapperImpl implements TacCaseAttachmentUploadMapper {

    private final ModelMapper modelMapper;

    public TacCaseAttachmentUploadMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public TacCaseAttachmentUploadDto mapTo(TacCaseAttachmentEntity attachmentEntity) {
        return modelMapper.map(attachmentEntity, TacCaseAttachmentUploadDto.class);
    }

    @Override
    public TacCaseAttachmentEntity mapFrom(TacCaseAttachmentUploadDto tacCaseAttachmentUploadDto) {
        return modelMapper.map(tacCaseAttachmentUploadDto, TacCaseAttachmentEntity.class);
    }

    public void mapFrom(TacCaseAttachmentUploadDto tacCaseAttachmentUploadDto, TacCaseAttachmentEntity attachmentEntity) {
        modelMapper.map(tacCaseAttachmentUploadDto, attachmentEntity);
    }
}
