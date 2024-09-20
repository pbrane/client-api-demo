package com.beaconstrategists.clientcaseapi.controller;

import com.beaconstrategists.clientcaseapi.api.TacCaseApi;
import com.beaconstrategists.clientcaseapi.model.*;
import com.beaconstrategists.clientcaseapi.service.TacCaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;

@RestController
public class TacCaseApiController implements TacCaseApi {

    private final TacCaseService tacCaseService;

    // Constructor Injection for better testability and immutability
    public TacCaseApiController(TacCaseService tacCaseService) {
        this.tacCaseService = tacCaseService;
    }

    @Override
    public ResponseEntity<TacCase> createTacCase(@Valid TacCaseCreate tacCaseCreate) {
        TacCase createdTacCase = tacCaseService.createTacCase(tacCaseCreate);
        return new ResponseEntity<>(createdTacCase, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Note> createTacCaseNote(String caseNumber, @Valid NoteCreate noteCreate) {
        Note createdNote = tacCaseService.createTacCaseNote(caseNumber, noteCreate);
        return new ResponseEntity<>(createdNote, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<TacCase> retrieveTacCase(String caseNumber, String fields, Boolean includeNotes) {
        TacCase tacCase = tacCaseService.retrieveTacCase(caseNumber, fields, includeNotes);
        return new ResponseEntity<>(tacCase, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Attachment> retrieveTacCaseAttachment(String caseNumber, String id, String fields) {
        Attachment attachment = tacCaseService.retrieveTacCaseAttachment(caseNumber, id, fields);
        return new ResponseEntity<>(attachment, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Attachment>> retrieveTacCaseAttachments(String caseNumber, Integer offset, Integer limit) {
        List<Attachment> attachments = tacCaseService.retrieveTacCaseAttachments(caseNumber, offset, limit);
        return new ResponseEntity<>(attachments, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Note> retrieveTacCaseNote(String caseNumber, String id) {
        Note note = tacCaseService.retrieveTacCaseNote(caseNumber, id);
        return new ResponseEntity<>(note, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Note>> retrieveTacCaseNotes(String caseNumber, Integer offset, Integer limit,
                                                           OffsetDateTime createdFrom, OffsetDateTime createdTo,
                                                           OffsetDateTime createdSince) {
        List<Note> notes = tacCaseService.retrieveTacCaseNotes(caseNumber, offset, limit, createdFrom, createdTo, createdSince);
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateTacCase(String caseNumber, TacCase tacCase) {
        tacCaseService.updateTacCase(caseNumber, tacCase);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> updateTacCaseNote(String caseNumber, String id, Note note) {
        tacCaseService.updateTacCaseNote(caseNumber, id, note);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<UploadTacCaseAttachment201Response> uploadTacCaseAttachment(String caseNumber,
                                                                                      MultipartFile file,
                                                                                      String description) {
        UploadTacCaseAttachment201Response response = tacCaseService.uploadTacCaseAttachment(caseNumber, file, description);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
