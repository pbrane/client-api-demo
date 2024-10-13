package com.beaconstrategists.clientcaseapi.controllers;

import com.beaconstrategists.clientcaseapi.controllers.dto.TacCaseAttachmentDetailDto;
import com.beaconstrategists.clientcaseapi.controllers.dto.TacCaseAttachmentSummaryDto;
import com.beaconstrategists.clientcaseapi.controllers.dto.TacCaseDto;
import com.beaconstrategists.clientcaseapi.services.TacCaseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/tacCases")
public class TacCaseController {

    private final TacCaseService tacCaseService;

    public TacCaseController(TacCaseService tacCaseService) {
        this.tacCaseService = tacCaseService;
    }

    @GetMapping(path = "/")
    public ResponseEntity<List<TacCaseDto>> listTacCases(
            @RequestParam(value = "caseNumber", required = false) String caseNumber) {
        if (caseNumber != null) {
            Optional<TacCaseDto> foundTacCase = tacCaseService.findByCaseNumber(caseNumber);
            return foundTacCase.map(tacCaseDto -> ResponseEntity
                    .ok(Collections.singletonList(tacCaseDto)))
                    .orElseGet(() -> ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(Collections.emptyList()));
        } else {
            List<TacCaseDto> tacCaseDtos = tacCaseService.findAll();
            return ResponseEntity.ok(tacCaseDtos);
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<TacCaseDto> getTacCase(@PathVariable Long id) {
        Optional<TacCaseDto> foundTacCase = tacCaseService.findById(id);
        return foundTacCase.map(tacCaseDto -> new ResponseEntity<>(tacCaseDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(path = "/")
    public ResponseEntity<TacCaseDto> createTacCase(@Valid @RequestBody TacCaseDto tacCaseDto) {
        TacCaseDto tacCaseDtoSaved = tacCaseService.save(tacCaseDto);
        return new ResponseEntity<>(tacCaseDtoSaved, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<TacCaseDto> fullUpdateTacCase(
            @PathVariable Long id,
            @Valid @RequestBody TacCaseDto tacCaseDto) {

        if (!tacCaseService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        TacCaseDto tacCaseDtoSaved = tacCaseService.save(tacCaseDto);
        return new ResponseEntity<>(tacCaseDtoSaved, HttpStatus.OK);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<TacCaseDto> partialUpdate(
            @PathVariable Long id,
            @Valid @RequestBody TacCaseDto tacCaseDto) {

        if (!tacCaseService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        TacCaseDto tacCaseDtoSaved = tacCaseService.partialUpdate(id, tacCaseDto);
        return new ResponseEntity<>(tacCaseDtoSaved, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteTacCase(@PathVariable Long id) {
        tacCaseService.delete(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Message", "TAC Case ID: " + id + " deleted.");
        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/{caseId}/attachments")
    public ResponseEntity<List<TacCaseAttachmentSummaryDto>> listAttachments(@PathVariable Long caseId) {
        List<TacCaseAttachmentSummaryDto> attachments = tacCaseService.listAttachments(caseId);
        return ResponseEntity.ok(attachments);
    }

    @GetMapping(path = "/{caseId}/attachments/{attachmentId}")
    public ResponseEntity<TacCaseAttachmentDetailDto> getAttachment(
            @PathVariable Long caseId,
            @PathVariable Long attachmentId) {
        Optional<TacCaseAttachmentDetailDto> attachmentDetail = tacCaseService.getAttachment(caseId, attachmentId);
        return attachmentDetail.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(path = "/{caseId}/attachments")
    public ResponseEntity<TacCaseAttachmentDetailDto> addAttachment(
            @PathVariable Long caseId,
            @Valid @RequestBody TacCaseAttachmentDetailDto tacCaseAttachmentDetailDto) {
        TacCaseAttachmentDetailDto savedAttachment = tacCaseService.addAttachment(caseId, tacCaseAttachmentDetailDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAttachment);
    }

    @PutMapping(path = "/{caseId}/attachments/{attachmentId}")
    public ResponseEntity<TacCaseAttachmentDetailDto> updateAttachment(
            @PathVariable Long caseId,
            @PathVariable Long attachmentId,
            @Valid @RequestBody TacCaseAttachmentDetailDto tacCaseAttachmentDetailDto) {
        TacCaseAttachmentDetailDto updatedAttachment = tacCaseService.updateAttachment(caseId, attachmentId, tacCaseAttachmentDetailDto);
        return ResponseEntity.ok(updatedAttachment);
    }

    @DeleteMapping(path = "/{caseId}/attachments/{attachmentId}")
    public ResponseEntity<Void> deleteAttachment(
            @PathVariable Long caseId,
            @PathVariable Long attachmentId) {
        tacCaseService.deleteAttachment(caseId, attachmentId);
        return ResponseEntity.noContent().build();
    }

}
