package com.qbitum.template.server.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.qbitum.template.server.model.SampleData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * SampleRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-01-09T15:39:05.889641620+05:30[Asia/Colombo]")
public class SampleRequest {

  private Integer number;

  @Valid
  private List<@Valid SampleData> idList = new ArrayList<>();

  public SampleRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public SampleRequest(Integer number, List<@Valid SampleData> idList) {
    this.number = number;
    this.idList = idList;
  }

  public SampleRequest number(Integer number) {
    this.number = number;
    return this;
  }

  /**
   * Get number
   * @return number
  */
  @NotNull 
  @Schema(name = "number", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("number")
  public Integer getNumber() {
    return number;
  }

  public void setNumber(Integer number) {
    this.number = number;
  }

  public SampleRequest idList(List<@Valid SampleData> idList) {
    this.idList = idList;
    return this;
  }

  public SampleRequest addIdListItem(SampleData idListItem) {
    if (this.idList == null) {
      this.idList = new ArrayList<>();
    }
    this.idList.add(idListItem);
    return this;
  }

  /**
   * Get idList
   * @return idList
  */
  @NotNull @Valid 
  @Schema(name = "idList", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("idList")
  public List<@Valid SampleData> getIdList() {
    return idList;
  }

  public void setIdList(List<@Valid SampleData> idList) {
    this.idList = idList;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SampleRequest sampleRequest = (SampleRequest) o;
    return Objects.equals(this.number, sampleRequest.number) &&
        Objects.equals(this.idList, sampleRequest.idList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(number, idList);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SampleRequest {\n");
    sb.append("    number: ").append(toIndentedString(number)).append("\n");
    sb.append("    idList: ").append(toIndentedString(idList)).append("\n");
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

