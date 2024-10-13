package com.beaconstrategists.clientcaseapi.config;

import com.beaconstrategists.clientcaseapi.controllers.dto.TacCaseAttachmentUploadDto;
import com.beaconstrategists.clientcaseapi.model.entities.TacCaseAttachmentEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Example: Customize mapping for AttachmentDetailDto
        modelMapper.addMappings(new PropertyMap<TacCaseAttachmentEntity, TacCaseAttachmentUploadDto>() {
            @Override
            protected void configure() {
                // Customize mappings if necessary
                // For example, map nested TacCaseEntity to tacCaseId in DTO
                // map(source.getTacCase().getId(), destination.getTacCaseId());
            }
        });

        return modelMapper;
    }
}
