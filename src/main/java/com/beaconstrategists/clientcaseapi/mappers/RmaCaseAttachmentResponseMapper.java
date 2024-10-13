package com.beaconstrategists.clientcaseapi.mappers;

import com.beaconstrategists.clientcaseapi.controllers.dto.RmaCaseAttachmentResponseDto;
import com.beaconstrategists.clientcaseapi.model.entities.RmaCaseAttachmentEntity;

public interface RmaCaseAttachmentResponseMapper {
    RmaCaseAttachmentResponseDto mapTo(RmaCaseAttachmentEntity entity);
}