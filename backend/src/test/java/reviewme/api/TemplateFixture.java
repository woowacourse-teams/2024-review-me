package reviewme.api;

import java.time.LocalDate;
import java.util.List;
import reviewme.question.domain.QuestionType;
import reviewme.review.service.dto.response.detail.OptionGroupAnswerResponse;
import reviewme.review.service.dto.response.detail.OptionItemAnswerResponse;
import reviewme.review.service.dto.response.detail.QuestionAnswerResponse;
import reviewme.review.service.dto.response.detail.SectionAnswerResponse;
import reviewme.review.service.dto.response.detail.TemplateAnswerResponse;
import reviewme.template.domain.VisibleType;
import reviewme.template.service.dto.response.OptionGroupResponse;
import reviewme.template.service.dto.response.OptionItemResponse;
import reviewme.template.service.dto.response.QuestionResponse;
import reviewme.template.service.dto.response.SectionResponse;
import reviewme.template.service.dto.response.TemplateResponse;

class TemplateFixture {

    public static TemplateResponse templateResponse() {
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
                1, "카테고리 선택", VisibleType.ALWAYS.name(), null, "아루와 함께 한 기억을 떠올려볼게요.", firstSectionQuestions
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
                2, "커뮤니케이션 능력", VisibleType.ALWAYS.name(), 1L, "아루의 커뮤니케이션, 협업 능력을 평가해주세요.", secondSectionQuestions
        );

        return new TemplateResponse(1, "아루", "리뷰미", List.of(firstSection, secondSection));
    }

    public static TemplateAnswerResponse templateAnswerResponse() {
        // Section 1
        List<OptionItemAnswerResponse> firstOptionAnswers = List.of(
                new OptionItemAnswerResponse(1, "커뮤니케이션, 협업 능력 (ex: 팀원간의 원활한 정보 공유, 명확한 의사소통)", true),
                new OptionItemAnswerResponse(2, "문제 해결 능력 (ex: 프로젝트 중 만난 버그/오류를 분석하고 이를 해결하는 능력)", false),
                new OptionItemAnswerResponse(3, "시간 관리 능력 (ex: 일정과 마감 기한 준수, 업무의 우선 순위 분배)", false)
        );
        OptionGroupAnswerResponse firstOptionGroupAnswer = new OptionGroupAnswerResponse(1, 1, 2, firstOptionAnswers);
        QuestionAnswerResponse firstQuestionAnswer = new QuestionAnswerResponse(
                1, true, QuestionType.CHECKBOX, "프로젝트 기간 동안, 아루의 강점이 드러났던 순간을 선택해주세요.", firstOptionGroupAnswer, null
        );
        SectionAnswerResponse firstSectionAnswer = new SectionAnswerResponse(
                1, "프로젝트 기간 동안, 아루의 강점이 드러났던 순간을 선택해주세요.", List.of(firstQuestionAnswer)
        );

        // Section 2
        List<OptionItemAnswerResponse> secondOptionAnswers = List.of(
                new OptionItemAnswerResponse(4, "반대 의견을 내더라도 듣는 사람이 기분 나쁘지 않게 이야기해요.", true),
                new OptionItemAnswerResponse(5, "팀원들의 의견을 잘 모아서 회의가 매끄럽게 진행되도록 해요.", false),
                new OptionItemAnswerResponse(6, "팀의 분위기를 주도해요.", true)
        );
        OptionGroupAnswerResponse secondOptionGroupAnswer = new OptionGroupAnswerResponse(2, 1, 3, secondOptionAnswers);
        QuestionAnswerResponse secondQuestionAnswer = new QuestionAnswerResponse(
                2, true, QuestionType.CHECKBOX, "커뮤니케이션, 협업 능력에서 어떤 부분이 인상 깊었는지 선택해주세요.", secondOptionGroupAnswer,
                "아루는 커뮤니케이션과 협업 능력에서 인상깊었어요~"
        );
        SectionAnswerResponse secondSectionAnswer = new SectionAnswerResponse(
                2, "커뮤니케이션, 협업 능력에서 어떤 부분이 인상 깊었는지 선택해주세요.", List.of(secondQuestionAnswer)
        );

        return new TemplateAnswerResponse(
                1, "아루", "리뷰미", LocalDate.of(2024, 8, 1), List.of(firstSectionAnswer, secondSectionAnswer)
        );
    }
}
