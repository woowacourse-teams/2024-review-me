package reviewme.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.requestCookies;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.restdocs.cookies.CookieDescriptor;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;
import reviewme.question.domain.QuestionType;
import reviewme.review.service.dto.request.ReviewRegisterRequest;
import reviewme.review.service.dto.response.gathered.AnswerContentResponse;
import reviewme.review.service.dto.response.gathered.ReviewsGatherByQuestionResponse;
import reviewme.review.service.dto.response.gathered.ReviewsGatherBySectionResponse;
import reviewme.review.service.dto.response.gathered.SimpleQuestionResponse;
import reviewme.review.service.dto.response.gathered.VoteResponse;
import reviewme.review.service.dto.response.list.ReceivedReviewsSummaryResponse;
import reviewme.review.service.dto.response.list.ReceivedReviewsResponse;
import reviewme.review.service.dto.response.list.ReviewCategoryResponse;
import reviewme.review.service.dto.response.list.ReviewListElementResponse;
import reviewme.review.service.exception.ReviewGroupNotFoundByReviewRequestCodeException;

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
        BDDMockito.given(reviewRegisterService.registerReview(any(ReviewRegisterRequest.class)))
                .willReturn(1L);

        FieldDescriptor[] requestFieldDescriptors = {
                fieldWithPath("reviewRequestCode").description("리뷰 요청 코드"),

                fieldWithPath("answers[]").description("답변 목록"),
                fieldWithPath("answers[].questionId").description("질문 ID"),
                fieldWithPath("answers[].selectedOptionIds").description("선택한 옵션 ID 목록").optional(),
                fieldWithPath("answers[].text").description("서술 답변").optional()
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
        BDDMockito.given(reviewRegisterService.registerReview(any(ReviewRegisterRequest.class)))
                .willThrow(new ReviewGroupNotFoundByReviewRequestCodeException(anyString()));

        FieldDescriptor[] requestFieldDescriptors = {
                fieldWithPath("reviewRequestCode").description("리뷰 요청 코드"),

                fieldWithPath("answers[]").description("답변 목록"),
                fieldWithPath("answers[].questionId").description("질문 ID"),
                fieldWithPath("answers[].selectedOptionIds").description("선택한 옵션 ID 목록").optional(),
                fieldWithPath("answers[].text").description("서술형 답변").optional()
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

    @Test
    void 자신이_받은_리뷰_한_개를_조회한다() {
        BDDMockito.given(reviewDetailLookupService.getReviewDetail(anyLong(), anyString()))
                .willReturn(TemplateFixture.templateAnswerResponse());

        ParameterDescriptor[] requestPathDescriptors = {
                parameterWithName("id").description("리뷰 ID")
        };

        CookieDescriptor[] cookieDescriptors = {
                cookieWithName("JSESSIONID").description("세션 쿠키")
        };

        FieldDescriptor[] responseFieldDescriptors = {
                fieldWithPath("createdAt").description("리뷰 작성 날짜"),
                fieldWithPath("formId").description("폼 ID"),
                fieldWithPath("revieweeName").description("리뷰이 이름"),
                fieldWithPath("projectName").description("프로젝트 이름"),

                fieldWithPath("sections[]").description("섹션 목록"),
                fieldWithPath("sections[].sectionId").description("섹션 ID"),
                fieldWithPath("sections[].header").description("섹션 제목"),

                fieldWithPath("sections[].questions[]").description("질문 목록"),
                fieldWithPath("sections[].questions[].questionId").description("질문 ID"),
                fieldWithPath("sections[].questions[].required").description("필수 여부"),
                fieldWithPath("sections[].questions[].content").description("질문 내용"),
                fieldWithPath("sections[].questions[].questionType").description("질문 타입"),

                fieldWithPath("sections[].questions[].optionGroup").description("옵션 그룹").optional(),
                fieldWithPath("sections[].questions[].optionGroup.optionGroupId").description("옵션 그룹 ID"),
                fieldWithPath("sections[].questions[].optionGroup.minCount").description("최소 선택 개수"),
                fieldWithPath("sections[].questions[].optionGroup.maxCount").description("최대 선택 개수"),

                fieldWithPath("sections[].questions[].optionGroup.options[]").description("선택 항목 목록"),
                fieldWithPath("sections[].questions[].optionGroup.options[].optionId").description("선택 항목 ID"),
                fieldWithPath("sections[].questions[].optionGroup.options[].content").description("선택 항목 내용"),
                fieldWithPath("sections[].questions[].optionGroup.options[].isChecked").description("선택 여부"),
                fieldWithPath("sections[].questions[].answer").description("서술형 답변").optional(),
        };

        RestDocumentationResultHandler handler = document(
                "review-detail-with-session",
                requestCookies(cookieDescriptors),
                pathParameters(requestPathDescriptors),
                responseFields(responseFieldDescriptors)
        );

        givenWithSpec().log().all()
                .pathParam("id", "1")
                .cookie("JSESSIONID", "AVEBNKLCL13TNVZ")
                .when().get("/v2/reviews/{id}")
                .then().log().all()
                .apply(handler)
                .statusCode(200);
    }

    @Test
    void 자신이_받은_리뷰_목록을_조회한다() {
        List<ReviewListElementResponse> receivedReviews = List.of(
                new ReviewListElementResponse(1L, LocalDate.of(2024, 8, 1), "(리뷰 미리보기 1)",
                        List.of(new ReviewCategoryResponse(1L, "카테고리 1"))),
                new ReviewListElementResponse(2L, LocalDate.of(2024, 8, 2), "(리뷰 미리보기 2)",
                        List.of(new ReviewCategoryResponse(2L, "카테고리 2")))
        );
        ReceivedReviewsResponse response = new ReceivedReviewsResponse(
                "아루3", "리뷰미", 1L, true, receivedReviews);
        BDDMockito.given(reviewListLookupService.getReceivedReviews(anyLong(), anyInt(), anyString()))
                .willReturn(response);

        CookieDescriptor[] cookieDescriptors = {
                cookieWithName("JSESSIONID").description("세션 쿠키")
        };

        ParameterDescriptor[] queryParameter = {
                parameterWithName("reviewRequestCode").description("리뷰 요청 코드"),
                parameterWithName("lastReviewId").description("페이지의 마지막 리뷰 ID - 기본으로 최신순 첫번째 페이지 응답"),
                parameterWithName("size").description("페이지의 크기 - 기본으로 10개씩 응답")
        };

        FieldDescriptor[] responseFieldDescriptors = {
                fieldWithPath("revieweeName").description("리뷰이 이름"),
                fieldWithPath("projectName").description("프로젝트 이름"),
                fieldWithPath("lastReviewId").description("페이지의 마지막 리뷰 ID"),
                fieldWithPath("isLastPage").description("마지막 페이지 여부"),

                fieldWithPath("reviews[]").description("리뷰 목록"),
                fieldWithPath("reviews[].reviewId").description("리뷰 ID"),
                fieldWithPath("reviews[].createdAt").description("리뷰 작성 날짜"),
                fieldWithPath("reviews[].contentPreview").description("리뷰 미리보기"),

                fieldWithPath("reviews[].categories[]").description("카테고리 목록"),
                fieldWithPath("reviews[].categories[].optionId").description("카테고리 ID"),
                fieldWithPath("reviews[].categories[].content").description("카테고리 내용")
        };

        RestDocumentationResultHandler handler = document(
                "received-review-list-with-pagination",
                requestCookies(cookieDescriptors),
                queryParameters(queryParameter),
                responseFields(responseFieldDescriptors)
        );

        givenWithSpec().log().all()
                .cookie("JSESSIONID", "ASVNE1VAKDNV4")
                .queryParam("reviewRequestCode", "hello!!")
                .queryParam("lastReviewId", "2")
                .queryParam("size", "5")
                .when().get("/v2/reviews")
                .then().log().all()
                .apply(handler)
                .statusCode(200);
    }

    @Test
    void 자신이_받은_리뷰의_요약를_조회한다() {
        BDDMockito.given(reviewSummaryService.getReviewSummary(anyString()))
                .willReturn(new ReceivedReviewsSummaryResponse("리뷰미", "산초", 5));

        CookieDescriptor[] cookieDescriptors = {
                cookieWithName("JSESSIONID").description("세션 쿠키")
        };

        FieldDescriptor[] responseFieldDescriptors = {
                fieldWithPath("projectName").description("프로젝트 이름"),
                fieldWithPath("revieweeName").description("리뷰어 이름"),
                fieldWithPath("totalReviewCount").description("받은 리뷰 전체 개수")
        };

        RestDocumentationResultHandler handler = document(
                "received-review-summary",
                requestCookies(cookieDescriptors),
                responseFields(responseFieldDescriptors)
        );

        givenWithSpec().log().all()
                .cookie("JSESSIONID", "ABCDEFGHI1234")
                .when().get("/v2/reviews/summary")
                .then().log().all()
                .apply(handler)
                .statusCode(200);
    }

    @Test
    void 자신이_받은_리뷰의_요약를_섹션별로_조회한다() {
        ReviewsGatherBySectionResponse response = new ReviewsGatherBySectionResponse(List.of(
                new ReviewsGatherByQuestionResponse(
                        new SimpleQuestionResponse("서술형 질문", QuestionType.TEXT),
                        List.of(
                                new AnswerContentResponse("산초의 답변"),
                                new AnswerContentResponse("삼촌의 답변")),
                        null),
                new ReviewsGatherByQuestionResponse(
                        new SimpleQuestionResponse("선택형 질문", QuestionType.CHECKBOX),
                        null,
                        List.of(
                                new VoteResponse("짜장", 3),
                                new VoteResponse("짬뽕", 5))))
        );
        BDDMockito.given(gatheredReviewLookupService.getReceivedReviewsBySectionId(anyString(), anyLong()))
                .willReturn(response);

        CookieDescriptor[] cookieDescriptors = {
                cookieWithName("JSESSIONID").description("세션 쿠키")
        };
        ParameterDescriptor[] queryParameterDescriptors = {
                parameterWithName("sectionId").description("섹션 ID")
        };
        FieldDescriptor[] responseFieldDescriptors = {
                fieldWithPath("reviews").description("리뷰 목록"),
                fieldWithPath("reviews[].question").description("질문 정보"),
                fieldWithPath("reviews[].question.name").description("질문 이름"),
                fieldWithPath("reviews[].question.type").description("질문 유형"),
                fieldWithPath("reviews[].answers").description("서술형 답변 목록").optional(),
                fieldWithPath("reviews[].answers[].content").description("서술형 답변 내용"),
                fieldWithPath("reviews[].votes").description("객관식 답변 목록").optional(),
                fieldWithPath("reviews[].votes[].content").description("객관식 항목"),
                fieldWithPath("reviews[].votes[].count").description("선택한 사람 수"),
        };
        RestDocumentationResultHandler handler = document(
                "received-review-by-section",
                requestCookies(cookieDescriptors),
                queryParameters(queryParameterDescriptors),
                responseFields(responseFieldDescriptors)
        );

        givenWithSpec().log().all()
                .cookie("JSESSIONID", "ABCDEFGHI1234")
                .queryParam("sectionId", 1)
                .when().get("/v2/reviews/gather")
                .then().log().all()
                .apply(handler)
                .statusCode(200);
    }
}
