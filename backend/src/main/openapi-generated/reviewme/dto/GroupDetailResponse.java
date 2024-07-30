package reviewme.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import reviewme.dto.KeywordResponse;
import reviewme.dto.QuestionResponse;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * GroupDetailResponse
 */
@lombok.AllArgsConstructor

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.7.0")
public class GroupDetailResponse {

  private String revieweeName;

  private String projectName;

  @Valid
  private List<@Valid QuestionResponse> questions = new ArrayList<>();

  @Valid
  private List<@Valid KeywordResponse> keywords = new ArrayList<>();

  public GroupDetailResponse revieweeName(String revieweeName) {
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

  public GroupDetailResponse projectName(String projectName) {
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

  public GroupDetailResponse questions(List<@Valid QuestionResponse> questions) {
    this.questions = questions;
    return this;
  }

  public GroupDetailResponse addQuestionsItem(QuestionResponse questionsItem) {
    if (this.questions == null) {
      this.questions = new ArrayList<>();
    }
    this.questions.add(questionsItem);
    return this;
  }

  /**
   * Get questions
   * @return questions
   */
  @Valid 
  @Schema(name = "questions", example = "[{\"id\":16,\"content\":\"소프트스킬은 어떤가요?\"},{\"id\":24,\"content\":\"팀원과의 관계는 어떤가요?\"}]", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("questions")
  public List<@Valid QuestionResponse> getQuestions() {
    return questions;
  }

  public void setQuestions(List<@Valid QuestionResponse> questions) {
    this.questions = questions;
  }

  public GroupDetailResponse keywords(List<@Valid KeywordResponse> keywords) {
    this.keywords = keywords;
    return this;
  }

  public GroupDetailResponse addKeywordsItem(KeywordResponse keywordsItem) {
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
    GroupDetailResponse groupDetailResponse = (GroupDetailResponse) o;
    return Objects.equals(this.revieweeName, groupDetailResponse.revieweeName) &&
        Objects.equals(this.projectName, groupDetailResponse.projectName) &&
        Objects.equals(this.questions, groupDetailResponse.questions) &&
        Objects.equals(this.keywords, groupDetailResponse.keywords);
  }

  @Override
  public int hashCode() {
    return Objects.hash(revieweeName, projectName, questions, keywords);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GroupDetailResponse {\n");
    sb.append("    revieweeName: ").append(toIndentedString(revieweeName)).append("\n");
    sb.append("    projectName: ").append(toIndentedString(projectName)).append("\n");
    sb.append("    questions: ").append(toIndentedString(questions)).append("\n");
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

