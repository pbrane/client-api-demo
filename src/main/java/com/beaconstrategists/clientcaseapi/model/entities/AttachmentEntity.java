package com.beaconstrategists.clientcaseapi.model.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "attachments")
public class AttachmentEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @JsonProperty("href")
  private String href;

  @JsonProperty("attachmentType")
  private String attachmentType;

  @JsonProperty("content")
  private String content;

  @JsonProperty("description")
  private String description;

  @JsonProperty("mimeType")
  private String mimeType;

  @JsonProperty("name")
  private String name;

  @JsonProperty("url")
  private String url;

  @JsonProperty("size")
  private Float size;

  @JsonProperty("atBaseType")
  private String atBaseType;

  @JsonProperty("atSchemaLocation")
  private URI atSchemaLocation;

  @JsonProperty("atType")
  private String atType;

}

