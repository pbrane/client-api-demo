package com.beaconstrategists.clientcaseapi.mappers.impl;

import com.beaconstrategists.clientcaseapi.controllers.dto.TacCaseAttachmentSummaryDto;
import com.beaconstrategists.clientcaseapi.mappers.TacCaseAttachmentSummaryMapper;
import com.beaconstrategists.clientcaseapi.model.entities.TacCaseAttachmentEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TacCaseAttachmentSummaryMapperImpl implements TacCaseAttachmentSummaryMapper {

    private final ModelMapper modelMapper;

    public TacCaseAttachmentSummaryMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public TacCaseAttachmentSummaryDto mapTo(TacCaseAttachmentEntity attachmentEntity) {
        return modelMapper.map(attachmentEntity, TacCaseAttachmentSummaryDto.class);
    }

    @Override
    public TacCaseAttachmentEntity mapFrom(TacCaseAttachmentSummaryDto tacCaseAttachmentSummaryDto) {
        return modelMapper.map(tacCaseAttachmentSummaryDto, TacCaseAttachmentEntity.class);
    }
}
