package com.beaconstrategists.clientcaseapi.service;

import com.beaconstrategists.clientcaseapi.model.*;
import com.beaconstrategists.clientcaseapi.repository.TacCaseRepository;
import com.beaconstrategists.clientcaseapi.repository.NoteRepository;
import com.beaconstrategists.clientcaseapi.repository.AttachmentRepository;
import com.beaconstrategists.clientcaseapi.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class TacCaseServiceImpl implements TacCaseService {

    private final TacCaseRepository tacCaseRepository;
    private final NoteRepository noteRepository;
    private final AttachmentRepository attachmentRepository;

    public TacCaseServiceImpl(TacCaseRepository tacCaseRepository,
                              NoteRepository noteRepository,
                              AttachmentRepository attachmentRepository) {
        this.tacCaseRepository = tacCaseRepository;
        this.noteRepository = noteRepository;
        this.attachmentRepository = attachmentRepository;
    }

    @Override
    public TacCase createTacCase(TacCaseCreate tacCaseCreate) {
        TacCase tacCase = new TacCase();
        // Map fields from tacCaseCreate to tacCase
        tacCase.setSubject(tacCaseCreate.getSubject());
        // ... map other fields
        return tacCaseRepository.save(tacCase);
    }

    @Override
    public Note createTacCaseNote(String caseNumber, NoteCreate noteCreate) {
        TacCase tacCase = tacCaseRepository.findByCaseNumber(caseNumber)
                .orElseThrow(() -> new ResourceNotFoundException("TAC Case not found"));
        Note note = new Note();
/*
  TODO
     note.setTacCase(tacCase);
     note.setContent(noteCreate.getContent());
     // ... map other fields
*/
        return noteRepository.save(note);
    }

    @Override
    public TacCase retrieveTacCase(String caseNumber, String fields, Boolean includeNotes) {
        TacCase tacCase = tacCaseRepository.findByCaseNumber(caseNumber)
                .orElseThrow(() -> new ResourceNotFoundException("TAC Case not found"));
        // Optionally handle 'fields' and 'includeNotes' for selective attribute retrieval
        return tacCase;
    }

    @Override
    public Attachment retrieveTacCaseAttachment(String caseNumber, String id, String fields) {
        // Implement retrieval logic
        return attachmentRepository.findByIdAndCaseNumber(id, caseNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Attachment not found"));
    }

    @Override
    public List<Attachment> retrieveTacCaseAttachments(String caseNumber, Integer offset, Integer limit) {
        // Implement pagination and retrieval logic
        return attachmentRepository.findByCaseNumber(caseNumber, offset, limit);
    }

    @Override
    public Note retrieveTacCaseNote(String caseNumber, String id) {
        // Implement retrieval logic
        return noteRepository.findByIdAndCaseNumber(id, caseNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found"));
    }

    @Override
    public List<Note> retrieveTacCaseNotes(String caseNumber, Integer offset, Integer limit,
                                           OffsetDateTime createdFrom, OffsetDateTime createdTo,
                                           OffsetDateTime createdSince) {
        // Implement retrieval logic with filtering
        return noteRepository.findNotes(caseNumber, offset, limit, createdFrom, createdTo, createdSince);
    }

    @Override
    public void updateTacCase(String caseNumber, TacCase tacCase) {
        TacCase existingTacCase = tacCaseRepository.findByCaseNumber(caseNumber)
                .orElseThrow(() -> new ResourceNotFoundException("TAC Case not found"));
        // Update fields
        existingTacCase.setSubject(tacCase.getSubject());
        // ... update other fields
        tacCaseRepository.save(existingTacCase);
    }

    @Override
    public void updateTacCaseNote(String caseNumber, String id, Note note) {
        Note existingNote = noteRepository.findByIdAndCaseNumber(id, caseNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found"));
/*
 TODO
      existingNote.setContent(note.getContent());
      ... update other fields
*/
        noteRepository.save(existingNote);
    }

    @Override
    public UploadTacCaseAttachment201Response uploadTacCaseAttachment(String caseNumber, MultipartFile file, String description) {
        // Implement file upload logic
        TacCase tacCase = tacCaseRepository.findByCaseNumber(caseNumber)
                .orElseThrow(() -> new ResourceNotFoundException("TAC Case not found"));

        Attachment attachment = new Attachment();
/*
 TODO
     attachment.setTacCase(tacCase);
     attachment.setDescription(description);
     attachment.setFileName(file.getOriginalFilename());
     attachment.setMimeType(file.getContentType());
     attachment.setSize(file.getSize());
 ... handle file storage (e.g., save to disk or cloud storage)
*/
        Attachment savedAttachment = attachmentRepository.save(attachment);

        UploadTacCaseAttachment201Response response = new UploadTacCaseAttachment201Response();
        response.setAttachmentId(savedAttachment.getId());
        response.setHref(URI.create("/tacCases/" + caseNumber + "/attachments/" + savedAttachment.getId()));

        return response;
    }
}
