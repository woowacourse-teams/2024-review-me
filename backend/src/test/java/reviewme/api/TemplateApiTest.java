package reviewme.api;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;
import reviewme.question.domain.QuestionType;
import reviewme.review.domain.exception.ReviewGroupNotFoundByRequestReviewCodeException;
import reviewme.template.domain.VisibleType;
import reviewme.template.service.dto.response.OptionGroupResponse;
import reviewme.template.service.dto.response.OptionItemResponse;
import reviewme.template.service.dto.response.QuestionResponse;
import reviewme.template.service.dto.response.SectionResponse;
import reviewme.template.service.dto.response.TemplateResponse;

class TemplateApiTest extends ApiTest {

    @Test
    void 리뷰_작성을_위한_템플릿을_반환한다() {
        // Section 1
        List<OptionItemResponse> firstSectionOptions = List.of(
                new OptionItemResponse(1, "커뮤니케이션, 협업 능력 (ex: 팀원간의 원활한 정보 공유, 명확한 의사소통)"),
                new OptionItemResponse(2, "문제 해결 능력 (ex: 프로젝트 중 만난 버그/오류를 분석하고 이를 해결하는 능력)"),
                new OptionItemResponse(3, "시간 관리 능력 (ex: 일정과 마감 기한 준수, 업무의 우선 순위 분배)")
        );
        List<QuestionResponse> firstSectionQuestions = List.of(
                new QuestionResponse(
                        1,
                        true,
                        "프로젝트 기간 동안, 아루의 강점이 드러났던 순간을 선택해주세요.",
                        QuestionType.CHECKBOX.name(),
                        new OptionGroupResponse(1, 1, 2, firstSectionOptions),
                        false,
                        null
                )
        );
        SectionResponse firstSection = new SectionResponse(
                1, VisibleType.ALWAYS.name(), null, "아루와 함께 한 기억을 떠올려볼게요.", firstSectionQuestions
        );

        // Section 2
        List<OptionItemResponse> secondSectionOptions = List.of(
                new OptionItemResponse(4, "반대 의견을 내더라도 듣는 사람이 기분 나쁘지 않게 이야기해요."),
                new OptionItemResponse(5, "팀원들의 의견을 잘 모아서 회의가 매끄럽게 진행되도록 해요."),
                new OptionItemResponse(6, "팀의 분위기를 주도해요.")
        );
        List<QuestionResponse> secondSectionQuestions = List.of(
                new QuestionResponse(
                        2,
                        true,
                        "커뮤니케이션, 협업 능력에서 어떤 부분이 인상 깊었는지 선택해주세요.",
                        QuestionType.CHECKBOX.name(),
                        new OptionGroupResponse(2, 1, 3, secondSectionOptions),
                        false,
                        null
                ),
                new QuestionResponse(
                        3,
                        true,
                        "위에서 선택한 사항에 대해 조금 더 자세히 설명해주세요.",
                        QuestionType.TEXT.name(),
                        null,
                        true,
                        "상황을 자세하게 기록할수록 아루에게 도움이 돼요. 아루 덕분에 팀이 원활한 소통을 이뤘거나, 함께 일하면서 배울 점이 있었는지 떠올려 보세요."
                )
        );
        SectionResponse secondSection = new SectionResponse(
                2, VisibleType.ALWAYS.name(), 1L, "아루의 커뮤니케이션, 협업 능력을 평가해주세요.", secondSectionQuestions
        );

        BDDMockito.given(templateService.generateReviewForm(anyString()))
                .willReturn(new TemplateResponse(1, "아루", "리뷰미", List.of(firstSection, secondSection)));

        ParameterDescriptor[] requestParameterDescriptors = {
                parameterWithName("reviewRequestCode").description("리뷰 요청 코드")
        };

        FieldDescriptor[] responseFieldDescriptors = {
                fieldWithPath("formId").description("폼 ID"),
                fieldWithPath("revieweeName").description("리뷰어 이름"),
                fieldWithPath("projectName").description("프로젝트 이름"),

                fieldWithPath("sections[].sectionId").description("섹션 ID"),
                fieldWithPath("sections[].visible").description("섹션 표시 여부"),
                fieldWithPath("sections[].onSelectedOptionId").description("섹션이 보이기 위한 선택 항목 ID").optional(),
                fieldWithPath("sections[].header").description("섹션 제목"),

                fieldWithPath("sections[].questions[].questionId").description("질문 ID"),
                fieldWithPath("sections[].questions[].required").description("필수 여부"),
                fieldWithPath("sections[].questions[].content").description("질문 내용"),
                fieldWithPath("sections[].questions[].questionType").description("질문 타입"),
                fieldWithPath("sections[].questions[].hasGuideline").description("가이드라인 존재 여부"),
                fieldWithPath("sections[].questions[].guideline").description("가이드라인 내용").optional(),
                fieldWithPath("sections[].questions[].optionGroup").description("옵션 그룹").optional(),

                fieldWithPath("sections[].questions[].optionGroup.optionGroupId").description("옵션 그룹 ID"),
                fieldWithPath("sections[].questions[].optionGroup.minCount").description("최소 선택 개수"),
                fieldWithPath("sections[].questions[].optionGroup.maxCount").description("최대 선택 개수"),

                fieldWithPath("sections[].questions[].optionGroup.options[].optionId").description("선택 항목 ID"),
                fieldWithPath("sections[].questions[].optionGroup.options[].content").description("선택 항목 내용"),
        };

        RestDocumentationResultHandler handler = document(
                "get-review-form",
                queryParameters(requestParameterDescriptors),
                responseFields(responseFieldDescriptors)
        );

        givenWithSpec().log().all()
                .queryParam("reviewRequestCode", "ABCD1234")
                .when().get("/v2/reviews/write")
                .then().log().all()
                .apply(handler)
                .statusCode(200);
    }

    @Test
    void 리뷰_그룹이_존재하지_않는_경우_예외를_반환한다() {
        BDDMockito.given(templateService.generateReviewForm(anyString()))
                .willThrow(new ReviewGroupNotFoundByRequestReviewCodeException(anyString()));

        ParameterDescriptor[] requestParameterDescriptors = {
                parameterWithName("reviewRequestCode").description("리뷰 요청 코드")
        };

        RestDocumentationResultHandler handler = document(
                "get-review-form-not-found",
                queryParameters(requestParameterDescriptors)
        );

        givenWithSpec().log().all()
                .queryParam("reviewRequestCode", "ABCD1234")
                .when().get("/v2/reviews/write")
                .then().log().all()
                .apply(handler)
                .statusCode(404);
    }
}
