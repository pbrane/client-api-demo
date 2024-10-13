package com.beaconstrategists.clientcaseapi.mappers.impl;

import com.beaconstrategists.clientcaseapi.controllers.dto.TacCaseAttachmentDetailDto;
import com.beaconstrategists.clientcaseapi.mappers.TacCaseAttachmentDetailMapper;
import com.beaconstrategists.clientcaseapi.model.entities.TacCaseAttachmentEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TacCaseAttachmentDetailMapperImpl implements TacCaseAttachmentDetailMapper {

    private final ModelMapper modelMapper;

    public TacCaseAttachmentDetailMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public TacCaseAttachmentDetailDto mapTo(TacCaseAttachmentEntity attachmentEntity) {
        return modelMapper.map(attachmentEntity, TacCaseAttachmentDetailDto.class);
    }

    @Override
    public TacCaseAttachmentEntity mapFrom(TacCaseAttachmentDetailDto tacCaseAttachmentDetailDto) {
        return modelMapper.map(tacCaseAttachmentDetailDto, TacCaseAttachmentEntity.class);
    }

    public void mapFrom(TacCaseAttachmentDetailDto tacCaseAttachmentDetailDto, TacCaseAttachmentEntity attachmentEntity) {
        modelMapper.map(tacCaseAttachmentDetailDto, attachmentEntity);
    }
}
