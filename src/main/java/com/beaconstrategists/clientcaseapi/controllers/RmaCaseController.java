package com.beaconstrategists.clientcaseapi.controllers;

import com.beaconstrategists.clientcaseapi.model.RmaCase;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log
public class RmaCaseController {

    @GetMapping(path = "/api/v1/rmaCases")
    public RmaCase retrieveRmaCases() {
        return RmaCase.builder()
                .caseId("999")
                .contactEmail("david@beaconstrategists.com")
                .shippedCarrier("UPS")
                .build();
    }

    @GetMapping(path = "/api/v1/rmaCases/{caseNumber}")
    public RmaCase retrieveRmaCase() {
        return RmaCase.builder()
                .caseId("888")
                .contactEmail("david@beaconstrategists.com")
                .shippedCarrier("FEDEX")
                .build();
    }

}
