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
 * ReviewContentRequest
 */
@lombok.AllArgsConstructor

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.7.0")
public class ReviewContentRequest {

  private Long questionId;

  private String answer;

  public ReviewContentRequest questionId(Long questionId) {
    this.questionId = questionId;
    return this;
  }

  /**
   * 질문 ID
   * @return questionId
   */
  
  @Schema(name = "questionId", example = "16", description = "질문 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("questionId")
  public Long getQuestionId() {
    return questionId;
  }

  public void setQuestionId(Long questionId) {
    this.questionId = questionId;
  }

  public ReviewContentRequest answer(String answer) {
    this.answer = answer;
    return this;
  }

  /**
   * 답변
   * @return answer
   */
  
  @Schema(name = "answer", example = "산초는 리뷰미 프로젝트에서 열심히 일했습니다. 이건 지피티가 아니라 진짜 리뷰입니다. ...", description = "답변", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("answer")
  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReviewContentRequest reviewContentRequest = (ReviewContentRequest) o;
    return Objects.equals(this.questionId, reviewContentRequest.questionId) &&
        Objects.equals(this.answer, reviewContentRequest.answer);
  }

  @Override
  public int hashCode() {
    return Objects.hash(questionId, answer);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReviewContentRequest {\n");
    sb.append("    questionId: ").append(toIndentedString(questionId)).append("\n");
    sb.append("    answer: ").append(toIndentedString(answer)).append("\n");
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

