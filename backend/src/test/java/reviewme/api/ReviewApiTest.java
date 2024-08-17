package reviewme.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;
import reviewme.review.domain.exception.ReviewGroupNotFoundByRequestReviewCodeException;
import reviewme.review.service.dto.request.CreateReviewRequest;

class ReviewApiTest extends ApiTest {

    private final String request = """
            {
                "reviewRequestCode": "ABCD1234",
                "answers": [
                    {
                        "questionId": 1,
                        "selectedOptionIds": [1, 2]
                    },
                    {
                        "questionId": 2,
                        "text": "답변 예시 1"
                    }
                ]
            }
            """;

    @Test
    void 리뷰를_등록한다() {
        BDDMockito.given(createReviewService.createReview(any(CreateReviewRequest.class)))
                .willReturn(1L);

        FieldDescriptor[] requestFieldDescriptors = {
                fieldWithPath("reviewRequestCode").description("리뷰 요청 코드"),
                fieldWithPath("answers[].questionId").description("질문 ID"),
                fieldWithPath("answers[].selectedOptionIds").description("선택한 옵션 ID 목록").optional(),
                fieldWithPath("answers[].text").description("텍스트 답변").optional()
        };

        RestDocumentationResultHandler handler = document(
                "create-review",
                requestFields(requestFieldDescriptors)
        );

        givenWithSpec().log().all()
                .body(request)
                .when().post("/v2/reviews")
                .then().log().all()
                .apply(handler)
                .statusCode(201);
    }

    @Test
    void 리뷰_그룹_코드가_올바르지_않은_경우_예외가_발생한다() {
        BDDMockito.given(createReviewService.createReview(any(CreateReviewRequest.class)))
                .willThrow(new ReviewGroupNotFoundByRequestReviewCodeException(anyString()));

        FieldDescriptor[] requestFieldDescriptors = {
                fieldWithPath("reviewRequestCode").description("리뷰 요청 코드"),
                fieldWithPath("answers[].questionId").description("질문 ID"),
                fieldWithPath("answers[].selectedOptionIds").description("선택한 옵션 ID 목록").optional(),
                fieldWithPath("answers[].text").description("텍스트 답변").optional()
        };

        RestDocumentationResultHandler handler = document(
                "create-review-invalid-review-request-code",
                requestFields(requestFieldDescriptors)
        );

        givenWithSpec().log().all()
                .body(request)
                .when().post("/v2/reviews")
                .then().log().all()
                .apply(handler)
                .statusCode(404);
    }
}
