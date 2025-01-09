package com.qbitum.template.server.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * SampleData
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-01-09T15:55:27.912939730+05:30[Asia/Colombo]")
public class SampleData {

  private String id1;

  private String id2;

  private String id3;

  public SampleData() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public SampleData(String id1, String id2, String id3) {
    this.id1 = id1;
    this.id2 = id2;
    this.id3 = id3;
  }

  public SampleData id1(String id1) {
    this.id1 = id1;
    return this;
  }

  /**
   * Get id1
   * @return id1
  */
  @NotNull 
  @Schema(name = "id1", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id1")
  public String getId1() {
    return id1;
  }

  public void setId1(String id1) {
    this.id1 = id1;
  }

  public SampleData id2(String id2) {
    this.id2 = id2;
    return this;
  }

  /**
   * Get id2
   * @return id2
  */
  @NotNull 
  @Schema(name = "id2", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id2")
  public String getId2() {
    return id2;
  }

  public void setId2(String id2) {
    this.id2 = id2;
  }

  public SampleData id3(String id3) {
    this.id3 = id3;
    return this;
  }

  /**
   * Get id3
   * @return id3
  */
  @NotNull 
  @Schema(name = "id3", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id3")
  public String getId3() {
    return id3;
  }

  public void setId3(String id3) {
    this.id3 = id3;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SampleData sampleData = (SampleData) o;
    return Objects.equals(this.id1, sampleData.id1) &&
        Objects.equals(this.id2, sampleData.id2) &&
        Objects.equals(this.id3, sampleData.id3);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id1, id2, id3);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SampleData {\n");
    sb.append("    id1: ").append(toIndentedString(id1)).append("\n");
    sb.append("    id2: ").append(toIndentedString(id2)).append("\n");
    sb.append("    id3: ").append(toIndentedString(id3)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

