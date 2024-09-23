package com.beaconstrategists.clientcaseapi.model.entities;

import com.beaconstrategists.clientcaseapi.model.TacCaseStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

/**
 * An RMA Case
 */
@Log
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "rma_cases")
public class RmaCaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = TacCaseEntity.class, cascade = CascadeType.ALL)
//  @JoinColumn(name = "id")
  private TacCaseEntity tacCase;

  private String href;

  private String caseNumber;

  private String caseId;

  private String relatedTacCaseId;

  private String requestType;

  private String newPartSerialNumber;

  private String faultySerialNumber;

  private String faultyPartNumber;

  private String returnedSerialNumber;

  private String returnedPartNumber;

  private TacCaseStatus caseStatus;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime newPartShippedDate;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime newPartDeliveredDate;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime faultyPartShippedDate;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime faultyPartDeliveredDate;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime failureAnalysisStartDate;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime failureAnalysisInProgressDate;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime failureAnalysisFinishedDate;

  private String shipToStreet1;

  private String shipToProvince;

  private String shipToPostalCode;

  private String shipToPhone;

  private String shipToCountry;

  private String shipToCity;

  private String shipToContactEmail;

  private String shipToAttention;

  private String shippedDate;

  private String shippedCarrier;

  private String problemDescription;

  private String installationCountry;

  private String customerTrackingNumber;

  private String contactEmail;

  private Integer vendorRmaNumber;
}