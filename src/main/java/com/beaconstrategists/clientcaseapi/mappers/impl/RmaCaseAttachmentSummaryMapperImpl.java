package com.beaconstrategists.clientcaseapi.mappers.impl;

import com.beaconstrategists.clientcaseapi.controllers.dto.RmaCaseAttachmentSummaryDto;
import com.beaconstrategists.clientcaseapi.mappers.RmaCaseAttachmentSummaryMapper;
import com.beaconstrategists.clientcaseapi.model.entities.RmaCaseAttachmentEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RmaCaseAttachmentSummaryMapperImpl implements RmaCaseAttachmentSummaryMapper {

    private final ModelMapper modelMapper;

    public RmaCaseAttachmentSummaryMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public RmaCaseAttachmentSummaryDto mapTo(RmaCaseAttachmentEntity attachmentEntity) {
        return modelMapper.map(attachmentEntity, RmaCaseAttachmentSummaryDto.class);
    }

    @Override
    public RmaCaseAttachmentEntity mapFrom(RmaCaseAttachmentSummaryDto rmaCaseAttachmentSummaryDto) {
        return modelMapper.map(rmaCaseAttachmentSummaryDto, RmaCaseAttachmentEntity.class);
    }
}
