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
 * ReviewContentResponse
 */
@lombok.AllArgsConstructor

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.7.0")
public class ReviewContentResponse {

  private Long id;

  private String question;

  private String answer;

  public ReviewContentResponse id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * 리뷰 내용 ID
   * @return id
   */
  
  @Schema(name = "id", example = "1", description = "리뷰 내용 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ReviewContentResponse question(String question) {
    this.question = question;
    return this;
  }

  /**
   * 질문
   * @return question
   */
  
  @Schema(name = "question", example = "소프트스킬은 어떤가요?", description = "질문", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("question")
  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public ReviewContentResponse answer(String answer) {
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
    ReviewContentResponse reviewContentResponse = (ReviewContentResponse) o;
    return Objects.equals(this.id, reviewContentResponse.id) &&
        Objects.equals(this.question, reviewContentResponse.question) &&
        Objects.equals(this.answer, reviewContentResponse.answer);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, question, answer);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReviewContentResponse {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    question: ").append(toIndentedString(question)).append("\n");
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

