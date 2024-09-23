package com.beaconstrategists.clientcaseapi.controllers;

import com.beaconstrategists.clientcaseapi.model.entities.RmaCaseEntity;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log
public class RmaCaseController {

    @PostMapping(path = "/api/v1/rmaCases")
    public RmaCaseEntity createRmaCase(@RequestBody RmaCaseEntity rmaCaseEntity) {
        return rmaCaseEntity;
    }

    @GetMapping(path = "/api/v1/rmaCases")
    public RmaCaseEntity retrieveRmaCases() {
        return RmaCaseEntity.builder()
                .caseId("999")
                .contactEmail("david@beaconstrategists.com")
                .shippedCarrier("UPS")
                .build();
    }

    @GetMapping(path = "/api/v1/rmaCases/{caseNumber}")
    public RmaCaseEntity retrieveRmaCase() {
        return RmaCaseEntity.builder()
                .caseId("888")
                .contactEmail("david@beaconstrategists.com")
                .shippedCarrier("FEDEX")
                .build();
    }

}
