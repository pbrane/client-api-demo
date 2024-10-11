package com.beaconstrategists.clientcaseapi.model.entities;

import com.beaconstrategists.clientcaseapi.model.CasePriorityEnum;
import com.beaconstrategists.clientcaseapi.model.TacCaseStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tac_cases", indexes = {@Index(name = "tac_cn_idx", columnList = "caseNumber", unique = true)})
public class TacCaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String href;

  private String caseNumber;

  private TacCaseStatus caseStatus;

  private Boolean rmaNeeded;

  private String subject;

  private Integer relatedRmaCount;

  private Integer relatedDispatchCount;

  private String problemDescription;

  private String installationCountry;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime firstResponseDate;

  private String customerTrackingNumber;

  private String contactEmail;

  private String productName;

  private String productSerialNumber;

  private String productFirmwareVersion;

  private String productSoftwareVersion;

  private String caseSolutionDescription;

  private CasePriorityEnum casePriority;

  private String caseOwner;

  private Integer caseNoteCount;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime caseCreatedDate;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime caseClosedDate;

  private String businessImpact;

  private String accountNumber;

  private String faultySerialNumber;

  private String faultyPartNumber;

  //TODO: fix
//  @Valid
//  private JsonNullable<List<@Valid NoteEntity>> notes = JsonNullable.<List<@Valid NoteEntity>>undefined();
}

