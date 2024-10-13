package com.beaconstrategists.clientcaseapi.controllers;

import com.beaconstrategists.clientcaseapi.controllers.dto.RmaCaseAttachmentDownloadDto;
import com.beaconstrategists.clientcaseapi.controllers.dto.RmaCaseAttachmentResponseDto;
import com.beaconstrategists.clientcaseapi.controllers.dto.RmaCaseAttachmentUploadDto;
import com.beaconstrategists.clientcaseapi.controllers.dto.RmaCaseDto;
import com.beaconstrategists.clientcaseapi.services.RmaCaseService;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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

    @GetMapping(path = "")
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

    @PostMapping(path = "")
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



    //Attachments

    @PostMapping("/{id}/attachments")
    public ResponseEntity<RmaCaseAttachmentResponseDto> uploadAttachment(
            @PathVariable Long id,
            @Valid @ModelAttribute RmaCaseAttachmentUploadDto uploadDto) {

        try {
            RmaCaseAttachmentResponseDto responseDto = rmaCaseService.addAttachment(id, uploadDto);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // Handle validation errors
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            // Handle file processing exceptions
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/attachments")
    public ResponseEntity<List<RmaCaseAttachmentResponseDto>> getAllAttachments(@PathVariable Long id) {
        List<RmaCaseAttachmentResponseDto> attachments = rmaCaseService.getAllAttachments(id);
        return new ResponseEntity<>(attachments, HttpStatus.OK);
    }

    @DeleteMapping("/{caseId}/attachments/{attachmentId}")
    public ResponseEntity<Void> deleteAttachment(
            @PathVariable Long caseId,
            @PathVariable Long attachmentId) {
        rmaCaseService.deleteAttachment(caseId, attachmentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}/attachments")
    public ResponseEntity<Void> deleteAllAttachments(@PathVariable Long id) {
        rmaCaseService.deleteAllAttachments(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{caseId}/attachments/{attachmentId}/download")
    public ResponseEntity<Resource> downloadAttachment(
            @PathVariable Long caseId,
            @PathVariable Long attachmentId) {
        RmaCaseAttachmentDownloadDto downloadDto = rmaCaseService.getAttachmentDownload(caseId, attachmentId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + downloadDto.getName() + "\"")
                .contentType(MediaType.parseMediaType(downloadDto.getMimeType()))
                .body(downloadDto.getResource());
    }

}
