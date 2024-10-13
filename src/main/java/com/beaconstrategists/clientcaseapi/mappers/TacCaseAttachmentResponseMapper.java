package com.beaconstrategists.clientcaseapi.mappers;

import com.beaconstrategists.clientcaseapi.controllers.dto.TacCaseAttachmentResponseDto;
import com.beaconstrategists.clientcaseapi.model.entities.TacCaseAttachmentEntity;

public interface TacCaseAttachmentResponseMapper {
    TacCaseAttachmentResponseDto mapTo(TacCaseAttachmentEntity entity);
}