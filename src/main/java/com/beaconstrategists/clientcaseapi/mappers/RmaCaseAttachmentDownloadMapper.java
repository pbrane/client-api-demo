package com.beaconstrategists.clientcaseapi.mappers;

import com.beaconstrategists.clientcaseapi.controllers.dto.RmaCaseAttachmentDownloadDto;
import com.beaconstrategists.clientcaseapi.model.entities.RmaCaseAttachmentEntity;

public interface RmaCaseAttachmentDownloadMapper {
    RmaCaseAttachmentDownloadDto mapTo(RmaCaseAttachmentEntity entity);
}
