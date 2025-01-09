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
 * ServerErrorResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-01-09T15:39:05.889641620+05:30[Asia/Colombo]")
public class ServerErrorResponse {

  private Integer code;

  private String status;

  private String message;

  private String errorPath;

  private String description;

  public ServerErrorResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ServerErrorResponse(Integer code, String status, String message) {
    this.code = code;
    this.status = status;
    this.message = message;
  }

  public ServerErrorResponse code(Integer code) {
    this.code = code;
    return this;
  }

  /**
   * Get code
   * @return code
  */
  @NotNull 
  @Schema(name = "code", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("code")
  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public ServerErrorResponse status(String status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
  */
  @NotNull 
  @Schema(name = "status", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("status")
  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public ServerErrorResponse message(String message) {
    this.message = message;
    return this;
  }

  /**
   * Get message
   * @return message
  */
  @NotNull 
  @Schema(name = "message", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("message")
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public ServerErrorResponse errorPath(String errorPath) {
    this.errorPath = errorPath;
    return this;
  }

  /**
   * Get errorPath
   * @return errorPath
  */
  
  @Schema(name = "errorPath", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("errorPath")
  public String getErrorPath() {
    return errorPath;
  }

  public void setErrorPath(String errorPath) {
    this.errorPath = errorPath;
  }

  public ServerErrorResponse description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  */
  
  @Schema(name = "description", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ServerErrorResponse serverErrorResponse = (ServerErrorResponse) o;
    return Objects.equals(this.code, serverErrorResponse.code) &&
        Objects.equals(this.status, serverErrorResponse.status) &&
        Objects.equals(this.message, serverErrorResponse.message) &&
        Objects.equals(this.errorPath, serverErrorResponse.errorPath) &&
        Objects.equals(this.description, serverErrorResponse.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, status, message, errorPath, description);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ServerErrorResponse {\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    errorPath: ").append(toIndentedString(errorPath)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
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

