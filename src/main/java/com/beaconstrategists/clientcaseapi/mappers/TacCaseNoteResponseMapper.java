package com.beaconstrategists.clientcaseapi.mappers;

import com.beaconstrategists.clientcaseapi.controllers.dto.TacCaseNoteResponseDto;
import com.beaconstrategists.clientcaseapi.model.entities.TacCaseNoteEntity;

public interface TacCaseNoteResponseMapper {
    TacCaseNoteResponseDto mapTo(TacCaseNoteEntity entity);
}