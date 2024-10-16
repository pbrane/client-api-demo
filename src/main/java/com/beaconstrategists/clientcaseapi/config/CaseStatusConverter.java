package com.beaconstrategists.clientcaseapi.config;

import com.beaconstrategists.clientcaseapi.model.CaseStatus;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class CaseStatusConverter implements Converter<String, CaseStatus> {

    @Override
    public CaseStatus convert(@NonNull String source) {
        return CaseStatus.fromValue(source);
    }
}
