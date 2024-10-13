package com.beaconstrategists.clientcaseapi.controllers.dto;

import lombok.Data;

@Data
public class RmaCaseAttachmentSummaryDto {
    private Long id;
    private String name;
    private String mimeType;
    private Float size;
    // Exclude 'description' and 'content' for summary
}
