package com.beaconstrategists.clientcaseapi.mappers;

import com.beaconstrategists.clientcaseapi.controllers.dto.TacCaseAttachmentDownloadDto;
import com.beaconstrategists.clientcaseapi.model.entities.TacCaseAttachmentEntity;

public interface TacCaseAttachmentDownloadMapper {
    TacCaseAttachmentDownloadDto mapTo(TacCaseAttachmentEntity entity);
}
