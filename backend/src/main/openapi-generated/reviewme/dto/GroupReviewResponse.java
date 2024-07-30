package reviewme.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import reviewme.dto.ReviewPreviewResponse;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * GroupReviewResponse
 */
@lombok.AllArgsConstructor

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.7.0")
public class GroupReviewResponse {

  private String revieweeName;

  private String projectName;

  @Valid
  private List<@Valid ReviewPreviewResponse> reviews = new ArrayList<>();

  public GroupReviewResponse revieweeName(String revieweeName) {
    this.revieweeName = revieweeName;
    return this;
  }

  /**
   * 리뷰이 이름
   * @return revieweeName
   */
  
  @Schema(name = "revieweeName", example = "산초", description = "리뷰이 이름", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("revieweeName")
  public String getRevieweeName() {
    return revieweeName;
  }

  public void setRevieweeName(String revieweeName) {
    this.revieweeName = revieweeName;
  }

  public GroupReviewResponse projectName(String projectName) {
    this.projectName = projectName;
    return this;
  }

  /**
   * 프로젝트 이름
   * @return projectName
   */
  
  @Schema(name = "projectName", example = "리뷰미", description = "프로젝트 이름", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("projectName")
  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public GroupReviewResponse reviews(List<@Valid ReviewPreviewResponse> reviews) {
    this.reviews = reviews;
    return this;
  }

  public GroupReviewResponse addReviewsItem(ReviewPreviewResponse reviewsItem) {
    if (this.reviews == null) {
      this.reviews = new ArrayList<>();
    }
    this.reviews.add(reviewsItem);
    return this;
  }

  /**
   * Get reviews
   * @return reviews
   */
  @Valid 
  @Schema(name = "reviews", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("reviews")
  public List<@Valid ReviewPreviewResponse> getReviews() {
    return reviews;
  }

  public void setReviews(List<@Valid ReviewPreviewResponse> reviews) {
    this.reviews = reviews;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GroupReviewResponse groupReviewResponse = (GroupReviewResponse) o;
    return Objects.equals(this.revieweeName, groupReviewResponse.revieweeName) &&
        Objects.equals(this.projectName, groupReviewResponse.projectName) &&
        Objects.equals(this.reviews, groupReviewResponse.reviews);
  }

  @Override
  public int hashCode() {
    return Objects.hash(revieweeName, projectName, reviews);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GroupReviewResponse {\n");
    sb.append("    revieweeName: ").append(toIndentedString(revieweeName)).append("\n");
    sb.append("    projectName: ").append(toIndentedString(projectName)).append("\n");
    sb.append("    reviews: ").append(toIndentedString(reviews)).append("\n");
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

