package reviewme.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * GroupCreationRequest
 */
@lombok.AllArgsConstructor

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.7.0")
public class GroupCreationRequest {

  private String revieweeName;

  private String projectName;

  public GroupCreationRequest revieweeName(String revieweeName) {
    this.revieweeName = revieweeName;
    return this;
  }

  /**
   * 리뷰이 이름
   * @return revieweeName
   */
  @Size(min = 1, max = 50) 
  @Schema(name = "revieweeName", example = "산초", description = "리뷰이 이름", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("revieweeName")
  public String getRevieweeName() {
    return revieweeName;
  }

  public void setRevieweeName(String revieweeName) {
    this.revieweeName = revieweeName;
  }

  public GroupCreationRequest projectName(String projectName) {
    this.projectName = projectName;
    return this;
  }

  /**
   * 프로젝트 이름
   * @return projectName
   */
  @Size(min = 1, max = 50) 
  @Schema(name = "projectName", example = "리뷰미", description = "프로젝트 이름", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("projectName")
  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GroupCreationRequest groupCreationRequest = (GroupCreationRequest) o;
    return Objects.equals(this.revieweeName, groupCreationRequest.revieweeName) &&
        Objects.equals(this.projectName, groupCreationRequest.projectName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(revieweeName, projectName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GroupCreationRequest {\n");
    sb.append("    revieweeName: ").append(toIndentedString(revieweeName)).append("\n");
    sb.append("    projectName: ").append(toIndentedString(projectName)).append("\n");
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

