package com.beaconstrategists.clientcaseapi.mappers.impl;

import com.beaconstrategists.clientcaseapi.controllers.dto.RmaCaseAttachmentDetailDto;
import com.beaconstrategists.clientcaseapi.mappers.RmaCaseAttachmentDetailMapper;
import com.beaconstrategists.clientcaseapi.model.entities.RmaCaseAttachmentEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RmaCaseAttachmentDetailMapperImpl implements RmaCaseAttachmentDetailMapper {

    private final ModelMapper modelMapper;

    public RmaCaseAttachmentDetailMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public RmaCaseAttachmentDetailDto mapTo(RmaCaseAttachmentEntity attachmentEntity) {
        return modelMapper.map(attachmentEntity, RmaCaseAttachmentDetailDto.class);
    }

    @Override
    public RmaCaseAttachmentEntity mapFrom(RmaCaseAttachmentDetailDto rmaCaseAttachmentDetailDto) {
        return modelMapper.map(rmaCaseAttachmentDetailDto, RmaCaseAttachmentEntity.class);
    }

    public void mapFrom(RmaCaseAttachmentDetailDto rmaCaseAttachmentDetailDto, RmaCaseAttachmentEntity attachmentEntity) {
        modelMapper.map(rmaCaseAttachmentDetailDto, attachmentEntity);
    }
}
