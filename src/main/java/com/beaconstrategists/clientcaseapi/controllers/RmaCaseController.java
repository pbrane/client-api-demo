package com.beaconstrategists.clientcaseapi.controllers;

import com.beaconstrategists.clientcaseapi.controllers.dto.RmaCaseAttachmentDetailDto;
import com.beaconstrategists.clientcaseapi.controllers.dto.RmaCaseAttachmentSummaryDto;
import com.beaconstrategists.clientcaseapi.controllers.dto.RmaCaseDto;
import com.beaconstrategists.clientcaseapi.services.RmaCaseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/rmaCases")
public class RmaCaseController {

    private final RmaCaseService rmaCaseService;

    public RmaCaseController(RmaCaseService rmaCaseService) {
        this.rmaCaseService = rmaCaseService;
    }

    @GetMapping(path = "/")
    public ResponseEntity<List<RmaCaseDto>> listRmaCases(
            @RequestParam(value = "caseNumber", required = false) String caseNumber) {
        if (caseNumber != null) {
            Optional<RmaCaseDto> foundRmaCase = rmaCaseService.findByCaseNumber(caseNumber);
            return foundRmaCase.map(rmaCaseDto -> ResponseEntity
                            .ok(Collections.singletonList(rmaCaseDto)))
                    .orElseGet(() -> ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(Collections.emptyList()));
        } else {
            List<RmaCaseDto> rmaCaseDtos = rmaCaseService.findAll();
            return ResponseEntity.ok(rmaCaseDtos);
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<RmaCaseDto> getRmaCase(@PathVariable Long id) {
        Optional<RmaCaseDto> foundRmaCase = rmaCaseService.findById(id);
        return foundRmaCase.map(rmaCaseDto -> new ResponseEntity<>(rmaCaseDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(path = "/")
    public ResponseEntity<RmaCaseDto> createRmaCase(@Valid @RequestBody RmaCaseDto rmaCaseDto) {
        RmaCaseDto rmaCaseDtoSaved = rmaCaseService.save(rmaCaseDto);
        return new ResponseEntity<>(rmaCaseDtoSaved, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<RmaCaseDto> fullUpdateRmaCase(
            @PathVariable Long id,
            @Valid @RequestBody RmaCaseDto rmaCaseDto) {

        if (!rmaCaseService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        RmaCaseDto rmaCaseDtoSaved = rmaCaseService.save(rmaCaseDto);
        return new ResponseEntity<>(rmaCaseDtoSaved, HttpStatus.OK);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<RmaCaseDto> partialUpdate(
            @PathVariable Long id,
            @Valid @RequestBody RmaCaseDto rmaCaseDto) {

        if (!rmaCaseService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        RmaCaseDto rmaCaseDtoSaved = rmaCaseService.partialUpdate(id, rmaCaseDto);
        return new ResponseEntity<>(rmaCaseDtoSaved, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteRmaCase(@PathVariable Long id) {
        rmaCaseService.delete(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Message", "RMA Case ID: " + id + " deleted.");
        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/{caseId}/attachments")
    public ResponseEntity<List<RmaCaseAttachmentSummaryDto>> listAttachments(@PathVariable Long caseId) {
        List<RmaCaseAttachmentSummaryDto> attachments = rmaCaseService.listAttachments(caseId);
        return ResponseEntity.ok(attachments);
    }

    @GetMapping(path = "/{caseId}/attachments/{attachmentId}")
    public ResponseEntity<RmaCaseAttachmentDetailDto> getAttachment(
            @PathVariable Long caseId,
            @PathVariable Long attachmentId) {
        Optional<RmaCaseAttachmentDetailDto> attachmentDetail = rmaCaseService.getAttachment(caseId, attachmentId);
        return attachmentDetail.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(path = "/{caseId}/attachments")
    public ResponseEntity<RmaCaseAttachmentDetailDto> addAttachment(
            @PathVariable Long caseId,
            @Valid @RequestBody RmaCaseAttachmentDetailDto rmaCaseAttachmentDetailDto) {
        RmaCaseAttachmentDetailDto savedAttachment = rmaCaseService.addAttachment(caseId, rmaCaseAttachmentDetailDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAttachment);
    }

    @PutMapping(path = "/{caseId}/attachments/{attachmentId}")
    public ResponseEntity<RmaCaseAttachmentDetailDto> updateAttachment(
            @PathVariable Long caseId,
            @PathVariable Long attachmentId,
            @Valid @RequestBody RmaCaseAttachmentDetailDto rmaCaseAttachmentDetailDto) {
        RmaCaseAttachmentDetailDto updatedAttachment = rmaCaseService.updateAttachment(caseId, attachmentId, rmaCaseAttachmentDetailDto);
        return ResponseEntity.ok(updatedAttachment);
    }

    @DeleteMapping(path = "/{caseId}/attachments/{attachmentId}")
    public ResponseEntity<Void> deleteAttachment(
            @PathVariable Long caseId,
            @PathVariable Long attachmentId) {
        rmaCaseService.deleteAttachment(caseId, attachmentId);
        return ResponseEntity.noContent().build();
    }

}
