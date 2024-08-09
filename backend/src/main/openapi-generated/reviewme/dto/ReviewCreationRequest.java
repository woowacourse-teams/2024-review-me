package reviewme.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import reviewme.dto.ReviewContentRequest;
import reviewme.dto.ReviewCreationRequestKeywordsInner;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ReviewCreationRequest
 */
@lombok.AllArgsConstructor

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.7.0")
public class ReviewCreationRequest {

  private String reviewRequestCode;

  @Valid
  private List<@Valid ReviewContentRequest> reviewContents = new ArrayList<>();

  @Valid
  private List<@Valid ReviewCreationRequestKeywordsInner> keywords = new ArrayList<>();

  public ReviewCreationRequest reviewRequestCode(String reviewRequestCode) {
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

  public ReviewCreationRequest reviewContents(List<@Valid ReviewContentRequest> reviewContents) {
    this.reviewContents = reviewContents;
    return this;
  }

  public ReviewCreationRequest addReviewContentsItem(ReviewContentRequest reviewContentsItem) {
    if (this.reviewContents == null) {
      this.reviewContents = new ArrayList<>();
    }
    this.reviewContents.add(reviewContentsItem);
    return this;
  }

  /**
   * Get reviewContents
   * @return reviewContents
   */
  @Valid 
  @Schema(name = "reviewContents", example = "[{\"questionId\":16,\"answer\":\"산초는 리뷰미 프로젝트에서 열심히 일했습니다. 이건 지피티가 아니라 진짜 리뷰입니다. ...\"},{\"questionId\":24,\"answer\":\"팀원과 관계 아주 좋아요~\"}]", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("reviewContents")
  public List<@Valid ReviewContentRequest> getReviewContents() {
    return reviewContents;
  }

  public void setReviewContents(List<@Valid ReviewContentRequest> reviewContents) {
    this.reviewContents = reviewContents;
  }

  public ReviewCreationRequest keywords(List<@Valid ReviewCreationRequestKeywordsInner> keywords) {
    this.keywords = keywords;
    return this;
  }

  public ReviewCreationRequest addKeywordsItem(ReviewCreationRequestKeywordsInner keywordsItem) {
    if (this.keywords == null) {
      this.keywords = new ArrayList<>();
    }
    this.keywords.add(keywordsItem);
    return this;
  }

  /**
   * 선택한 키워드 ID 목록
   * @return keywords
   */
  @Valid 
  @Schema(name = "keywords", example = "[14,15]", description = "선택한 키워드 ID 목록", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("keywords")
  public List<@Valid ReviewCreationRequestKeywordsInner> getKeywords() {
    return keywords;
  }

  public void setKeywords(List<@Valid ReviewCreationRequestKeywordsInner> keywords) {
    this.keywords = keywords;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReviewCreationRequest reviewCreationRequest = (ReviewCreationRequest) o;
    return Objects.equals(this.reviewRequestCode, reviewCreationRequest.reviewRequestCode) &&
        Objects.equals(this.reviewContents, reviewCreationRequest.reviewContents) &&
        Objects.equals(this.keywords, reviewCreationRequest.keywords);
  }

  @Override
  public int hashCode() {
    return Objects.hash(reviewRequestCode, reviewContents, keywords);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReviewCreationRequest {\n");
    sb.append("    reviewRequestCode: ").append(toIndentedString(reviewRequestCode)).append("\n");
    sb.append("    reviewContents: ").append(toIndentedString(reviewContents)).append("\n");
    sb.append("    keywords: ").append(toIndentedString(keywords)).append("\n");
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

