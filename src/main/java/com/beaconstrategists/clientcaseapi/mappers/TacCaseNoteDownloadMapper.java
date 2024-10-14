package com.beaconstrategists.clientcaseapi.mappers;

import com.beaconstrategists.clientcaseapi.controllers.dto.TacCaseNoteDownloadDto;
import com.beaconstrategists.clientcaseapi.model.entities.TacCaseNoteEntity;

public interface TacCaseNoteDownloadMapper {
    TacCaseNoteDownloadDto mapTo(TacCaseNoteEntity entity);
}
