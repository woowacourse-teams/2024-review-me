package reviewme.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import reviewme.dto.KeywordResponse;
import reviewme.dto.ReviewContentResponse;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * SingleReviewResponse
 */
@lombok.AllArgsConstructor

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.7.0")
public class SingleReviewResponse {

  private Long id;

  private LocalDate createdAt = null;

  private String projectName;

  private String revieweeName;

  @Valid
  private List<@Valid ReviewContentResponse> contents = new ArrayList<>();

  @Valid
  private List<@Valid KeywordResponse> keywords = new ArrayList<>();

  public SingleReviewResponse id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * 리뷰 ID
   * @return id
   */
  
  @Schema(name = "id", example = "1258", description = "리뷰 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public SingleReviewResponse createdAt(LocalDate createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * 리뷰 생성일
   * @return createdAt
   */
  @Valid 
  @Schema(name = "createdAt", example = "2021-08-01", description = "리뷰 생성일", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("createdAt")
  public LocalDate getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDate createdAt) {
    this.createdAt = createdAt;
  }

  public SingleReviewResponse projectName(String projectName) {
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

  public SingleReviewResponse revieweeName(String revieweeName) {
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

  public SingleReviewResponse contents(List<@Valid ReviewContentResponse> contents) {
    this.contents = contents;
    return this;
  }

  public SingleReviewResponse addContentsItem(ReviewContentResponse contentsItem) {
    if (this.contents == null) {
      this.contents = new ArrayList<>();
    }
    this.contents.add(contentsItem);
    return this;
  }

  /**
   * Get contents
   * @return contents
   */
  @Valid 
  @Schema(name = "contents", example = "[{\"id\":36,\"question\":\"소프트스킬은 어떤가요?\",\"answer\":\"산초는 리뷰미 프로젝트에서 열심히 일했습니다. 이건 지피티가 아니라 진짜 리뷰입니다. ...\"},{\"id\":48,\"question\":\"팀원과의 관계는 어떤가요?\",\"answer\":\"팀원과 관계 아주 좋아요~\"}]", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("contents")
  public List<@Valid ReviewContentResponse> getContents() {
    return contents;
  }

  public void setContents(List<@Valid ReviewContentResponse> contents) {
    this.contents = contents;
  }

  public SingleReviewResponse keywords(List<@Valid KeywordResponse> keywords) {
    this.keywords = keywords;
    return this;
  }

  public SingleReviewResponse addKeywordsItem(KeywordResponse keywordsItem) {
    if (this.keywords == null) {
      this.keywords = new ArrayList<>();
    }
    this.keywords.add(keywordsItem);
    return this;
  }

  /**
   * Get keywords
   * @return keywords
   */
  @Valid 
  @Schema(name = "keywords", example = "[{\"id\":1,\"content\":\"의견을 잘 조율해요\"},{\"id\":2,\"content\":\"활발하게 토의해요\"}]", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("keywords")
  public List<@Valid KeywordResponse> getKeywords() {
    return keywords;
  }

  public void setKeywords(List<@Valid KeywordResponse> keywords) {
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
    SingleReviewResponse singleReviewResponse = (SingleReviewResponse) o;
    return Objects.equals(this.id, singleReviewResponse.id) &&
        Objects.equals(this.createdAt, singleReviewResponse.createdAt) &&
        Objects.equals(this.projectName, singleReviewResponse.projectName) &&
        Objects.equals(this.revieweeName, singleReviewResponse.revieweeName) &&
        Objects.equals(this.contents, singleReviewResponse.contents) &&
        Objects.equals(this.keywords, singleReviewResponse.keywords);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, createdAt, projectName, revieweeName, contents, keywords);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SingleReviewResponse {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    projectName: ").append(toIndentedString(projectName)).append("\n");
    sb.append("    revieweeName: ").append(toIndentedString(revieweeName)).append("\n");
    sb.append("    contents: ").append(toIndentedString(contents)).append("\n");
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

