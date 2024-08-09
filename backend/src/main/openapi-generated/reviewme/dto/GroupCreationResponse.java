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
 * GroupCreationResponse
 */
@lombok.AllArgsConstructor

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.7.0")
public class GroupCreationResponse {

  private String reviewRequestCode;

  private String groupAccessCode;

  public GroupCreationResponse reviewRequestCode(String reviewRequestCode) {
    this.reviewRequestCode = reviewRequestCode;
    return this;
  }

  /**
   * 리뷰 요청 코드
   * @return reviewRequestCode
   */
  
  @Schema(name = "reviewRequestCode", example = "VN53pTY", description = "리뷰 요청 코드", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("reviewRequestCode")
  public String getReviewRequestCode() {
    return reviewRequestCode;
  }

  public void setReviewRequestCode(String reviewRequestCode) {
    this.reviewRequestCode = reviewRequestCode;
  }

  public GroupCreationResponse groupAccessCode(String groupAccessCode) {
    this.groupAccessCode = groupAccessCode;
    return this;
  }

  /**
   * 리뷰 그룹 확인 코드
   * @return groupAccessCode
   */
  
  @Schema(name = "groupAccessCode", example = "Pv3n42", description = "리뷰 그룹 확인 코드", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("groupAccessCode")
  public String getGroupAccessCode() {
    return groupAccessCode;
  }

  public void setGroupAccessCode(String groupAccessCode) {
    this.groupAccessCode = groupAccessCode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GroupCreationResponse groupCreationResponse = (GroupCreationResponse) o;
    return Objects.equals(this.reviewRequestCode, groupCreationResponse.reviewRequestCode) &&
        Objects.equals(this.groupAccessCode, groupCreationResponse.groupAccessCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(reviewRequestCode, groupAccessCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GroupCreationResponse {\n");
    sb.append("    reviewRequestCode: ").append(toIndentedString(reviewRequestCode)).append("\n");
    sb.append("    groupAccessCode: ").append(toIndentedString(groupAccessCode)).append("\n");
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

