package com.beaconstrategists.clientcaseapi.controllers.dto;

import lombok.Data;

@Data
public class RmaCaseAttachmentDetailDto {
    private Long id;
    private String name;
    private String mimeType;
    private String description;
    private Float size;
    private byte[] content; // Include binary data
}
