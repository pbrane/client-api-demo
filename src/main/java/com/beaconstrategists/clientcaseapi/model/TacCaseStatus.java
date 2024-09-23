package com.beaconstrategists.clientcaseapi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public enum TacCaseStatus {
  
  OPEN("open"),
  
  ACKNOWLEDGED("acknowledged"),
  
  REJECTED("rejected"),
  
  PENDING("pending"),
  
  HELD("held"),
  
  INPROGRESS("inprogress"),
  
  CANCELLED("cancelled"),
  
  CLOSED("closed"),
  
  RESOLVED("resolved");

  @JsonValue
  private String value;

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static TacCaseStatus fromValue(String value) {
    for (TacCaseStatus b : TacCaseStatus.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}

