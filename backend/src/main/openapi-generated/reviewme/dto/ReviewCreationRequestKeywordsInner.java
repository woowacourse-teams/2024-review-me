package reviewme.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ReviewCreationRequestKeywordsInner
 */
@lombok.AllArgsConstructor

@JsonTypeName("ReviewCreationRequest_keywords_inner")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.7.0")
public class ReviewCreationRequestKeywordsInner {

  private Long keywordId;

  public ReviewCreationRequestKeywordsInner keywordId(Long keywordId) {
    this.keywordId = keywordId;
    return this;
  }

  /**
   * 키워드 ID
   * @return keywordId
   */
  
  @Schema(name = "keywordId", example = "14", description = "키워드 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("keywordId")
  public Long getKeywordId() {
    return keywordId;
  }

  public void setKeywordId(Long keywordId) {
    this.keywordId = keywordId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReviewCreationRequestKeywordsInner reviewCreationRequestKeywordsInner = (ReviewCreationRequestKeywordsInner) o;
    return Objects.equals(this.keywordId, reviewCreationRequestKeywordsInner.keywordId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(keywordId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReviewCreationRequestKeywordsInner {\n");
    sb.append("    keywordId: ").append(toIndentedString(keywordId)).append("\n");
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

