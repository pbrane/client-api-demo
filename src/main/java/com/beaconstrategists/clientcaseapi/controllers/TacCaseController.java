package com.beaconstrategists.clientcaseapi.controllers;

import com.beaconstrategists.clientcaseapi.controllers.dto.TacCaseAttachmentDownloadDto;
import com.beaconstrategists.clientcaseapi.controllers.dto.TacCaseAttachmentResponseDto;
import com.beaconstrategists.clientcaseapi.controllers.dto.TacCaseAttachmentUploadDto;
import com.beaconstrategists.clientcaseapi.controllers.dto.TacCaseDto;
import com.beaconstrategists.clientcaseapi.services.TacCaseService;
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
@RequestMapping("/api/v1/tacCases")
public class TacCaseController {

    private final TacCaseService tacCaseService;

    public TacCaseController(TacCaseService tacCaseService) {
        this.tacCaseService = tacCaseService;
    }

    @GetMapping(path = "")
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

    @PostMapping(path = "")
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



    //Attachments

    @PostMapping("/{id}/attachments")
    public ResponseEntity<TacCaseAttachmentResponseDto> uploadAttachment(
            @PathVariable Long id,
            @Valid @ModelAttribute TacCaseAttachmentUploadDto uploadDto) {

        try {
            TacCaseAttachmentResponseDto responseDto = tacCaseService.addAttachment(id, uploadDto);
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
    public ResponseEntity<List<TacCaseAttachmentResponseDto>> getAllAttachments(@PathVariable Long id) {
        List<TacCaseAttachmentResponseDto> attachments = tacCaseService.getAllAttachments(id);
        return new ResponseEntity<>(attachments, HttpStatus.OK);
    }

    @DeleteMapping("/{caseId}/attachments/{attachmentId}")
    public ResponseEntity<Void> deleteAttachment(
            @PathVariable Long caseId,
            @PathVariable Long attachmentId) {
        tacCaseService.deleteAttachment(caseId, attachmentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}/attachments")
    public ResponseEntity<Void> deleteAllAttachments(@PathVariable Long id) {
        tacCaseService.deleteAllAttachments(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{caseId}/attachments/{attachmentId}/download")
    public ResponseEntity<Resource> downloadAttachment(
            @PathVariable Long caseId,
            @PathVariable Long attachmentId) {
        TacCaseAttachmentDownloadDto downloadDto = tacCaseService.getAttachmentDownload(caseId, attachmentId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + downloadDto.getName() + "\"")
                .contentType(MediaType.parseMediaType(downloadDto.getMimeType()))
                .body(downloadDto.getResource());
    }

}
