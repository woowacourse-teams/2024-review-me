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
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ReviewPreviewResponse
 */
@lombok.AllArgsConstructor

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.7.0")
public class ReviewPreviewResponse {

  private Long id;

  private LocalDate createdAt = null;

  private String contentPreview;

  @Valid
  private List<@Valid KeywordResponse> keywords = new ArrayList<>();

  public ReviewPreviewResponse id(Long id) {
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

  public ReviewPreviewResponse createdAt(LocalDate createdAt) {
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

  public ReviewPreviewResponse contentPreview(String contentPreview) {
    this.contentPreview = contentPreview;
    return this;
  }

  /**
   * 리뷰 내용 미리보기 (최대 150자)
   * @return contentPreview
   */
  
  @Schema(name = "contentPreview", example = "산초는 리뷰미 프로젝트에서 열심히 일했습니다. 이건 지피티가 아니라 진짜 리뷰입니다. ...", description = "리뷰 내용 미리보기 (최대 150자)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("contentPreview")
  public String getContentPreview() {
    return contentPreview;
  }

  public void setContentPreview(String contentPreview) {
    this.contentPreview = contentPreview;
  }

  public ReviewPreviewResponse keywords(List<@Valid KeywordResponse> keywords) {
    this.keywords = keywords;
    return this;
  }

  public ReviewPreviewResponse addKeywordsItem(KeywordResponse keywordsItem) {
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
    ReviewPreviewResponse reviewPreviewResponse = (ReviewPreviewResponse) o;
    return Objects.equals(this.id, reviewPreviewResponse.id) &&
        Objects.equals(this.createdAt, reviewPreviewResponse.createdAt) &&
        Objects.equals(this.contentPreview, reviewPreviewResponse.contentPreview) &&
        Objects.equals(this.keywords, reviewPreviewResponse.keywords);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, createdAt, contentPreview, keywords);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReviewPreviewResponse {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    contentPreview: ").append(toIndentedString(contentPreview)).append("\n");
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

