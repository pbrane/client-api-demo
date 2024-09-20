package com.beaconstrategists.clientcaseapi.service;

import com.beaconstrategists.clientcaseapi.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.OffsetDateTime;
import java.util.List;

public interface TacCaseService {
    TacCase createTacCase(TacCaseCreate tacCaseCreate);
    Note createTacCaseNote(String caseNumber, NoteCreate noteCreate);
    TacCase retrieveTacCase(String caseNumber, String fields, Boolean includeNotes);
    Attachment retrieveTacCaseAttachment(String caseNumber, String id, String fields);
    List<Attachment> retrieveTacCaseAttachments(String caseNumber, Integer offset, Integer limit);
    Note retrieveTacCaseNote(String caseNumber, String id);
    List<Note> retrieveTacCaseNotes(String caseNumber, Integer offset, Integer limit,
                                    OffsetDateTime createdFrom, OffsetDateTime createdTo,
                                    OffsetDateTime createdSince);
    void updateTacCase(String caseNumber, TacCase tacCase);
    void updateTacCaseNote(String caseNumber, String id, Note note);
    UploadTacCaseAttachment201Response uploadTacCaseAttachment(String caseNumber, MultipartFile file, String description);
}
