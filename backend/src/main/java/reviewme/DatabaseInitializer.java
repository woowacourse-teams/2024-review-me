package reviewme;

import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reviewme.template.domain.OptionGroup;
import reviewme.template.domain.OptionItem;
import reviewme.template.domain.OptionType;
import reviewme.template.domain.Question;
import reviewme.template.domain.QuestionType;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;
import reviewme.template.domain.VisibleType;
import reviewme.template.repository.TemplateRepository;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer {

    private static final String CATEGORY_HEADER = "이제, 선택한 순간들을 바탕으로 ${revieweeName}에 대한 리뷰를 작성해볼게요.";
    private static final String CATEGORY_TEXT_QUESTION = "위에서 선택한 사항과 관련된 경험을 구체적으로 적어 주세요.";
    private static final int KEYWORD_CHECKBOX_MIN_COUNT = 1;
    private static final int KEYWORD_CHECKBOX_MAX_COUNT = 2;

    private final TemplateRepository templateRepository;

    // TODO: 하드코딩되어 있는 ID를 사용하지 않도록 한다. Factory 혹은 Builder를 활용해 Template 하나를 저장하도록 한다.
    // TODO: 어드민 페이지를 활용해 Template을 관리하는 것이 추후 유지보수에 훨씬 이득일 수 있다.

    @PostConstruct
    @Transactional
    public void setup() {
        // 템플릿이 이미 존재하면 종료
        if (!templateRepository.findAll().isEmpty()) {
            return;
        }

        // 카테고리 선택 섹션
        OptionItem communicationOptionItem = new OptionItem("🗣️커뮤니케이션, 협업 능력 (예: 팀원간의 원활한 정보 공유, 명확한 의사소통)", 1, OptionType.CATEGORY);
        OptionItem problemSolvingOptionItem = new OptionItem("💡문제 해결 능력 (예: 프로젝트 중 만난 버그/오류를 분석하고 이를 해결하는 능력)", 2, OptionType.CATEGORY);
        OptionItem timeManagementOptionItem = new OptionItem("⏰시간 관리 능력 (예: 일정과 마감 기한 준수, 업무의 우선 순위 분배)", 3, OptionType.CATEGORY);
        OptionItem technicalOptionItem = new OptionItem("💻기술적 역량, 전문 지식 (예: 요구 사항을 이해하고 이를 구현하는 능력)", 4, OptionType.CATEGORY);
        OptionItem mindsetOptionItem = new OptionItem("🌱성장 마인드셋 (예: 새로운 분야나 잘 모르는 분야에 도전하는 마음, 꾸준한 노력으로 프로젝트 이전보다 성장하는 모습)", 5, OptionType.CATEGORY);
        OptionGroup categorySectionOptionGroup = new OptionGroup(
                List.of(communicationOptionItem, problemSolvingOptionItem, timeManagementOptionItem, technicalOptionItem, mindsetOptionItem),
                KEYWORD_CHECKBOX_MIN_COUNT,
                KEYWORD_CHECKBOX_MAX_COUNT
        );
        Question categorySectionQuestion = new Question(true, QuestionType.CHECKBOX, categorySectionOptionGroup, "프로젝트 기간 동안, ${revieweeName}의 강점이 드러났던 순간을 선택해주세요.", null, 1);
        Section categorySection = new Section(VisibleType.ALWAYS, List.of(categorySectionQuestion), null, "강점 발견", "${revieweeName/와:과} 함께 한 기억을 떠올려볼게요.", 1);

        // 커뮤니케이션 능력 섹션
        OptionGroup communicationOptionGroup = new OptionGroup(
                List.of(
                    new OptionItem("반대 의견을 내더라도 듣는 사람이 기분 나쁘지 않게 이야기해요.", 1, OptionType.KEYWORD),
                    new OptionItem("팀원들의 의견을 잘 모아서 회의가 매끄럽게 진행되도록 해요.", 2, OptionType.KEYWORD),
                    new OptionItem("팀의 분위기를 주도해요.", 3, OptionType.KEYWORD),
                    new OptionItem("주장을 이야기할 때에는 합당한 근거가 뒤따라요.", 4, OptionType.KEYWORD),
                    new OptionItem("팀에게 필요한 것과 그렇지 않은 것을 잘 구분해요.", 5, OptionType.KEYWORD),
                    new OptionItem("팀 내 주어진 요구사항에 우선순위를 잘 매겨요.", 6, OptionType.KEYWORD),
                    new OptionItem("서로 다른 분야간의 소통도 중요하게 생각해요.", 7, OptionType.KEYWORD)
                ),
                KEYWORD_CHECKBOX_MIN_COUNT,
                KEYWORD_CHECKBOX_MAX_COUNT
        );
        Question communicationSectionQuestion = new Question(true, QuestionType.CHECKBOX, communicationOptionGroup, "커뮤니케이션, 협업 능력에서 어떤 부분이 인상 깊었는지 선택해주세요.", null, 1);
        Question communicationSectionTextQuestion = new Question(true, QuestionType.TEXT, CATEGORY_TEXT_QUESTION, "상황을 자세하게 기록할수록 ${revieweeName}에게 도움이 돼요. ${revieweeName} 덕분에 팀이 원활한 소통을 이뤘거나, 함께 일하면서 배울 점이 있었는지 떠올려 보세요.", 2);
        Section communicationSection = new Section(VisibleType.CONDITIONAL, List.of(communicationSectionQuestion, communicationSectionTextQuestion), communicationOptionItem, "커뮤니케이션 능력", CATEGORY_HEADER, 2);

        // 문제해결 능력 섹션
        OptionGroup problemSolvingOptionGroup = new OptionGroup(
                List.of(
                    new OptionItem("큰 문제를 작은 단위로 쪼개서 단계별로 해결해나가요.", 1, OptionType.KEYWORD),
                    new OptionItem("낯선 문제를 만나도 당황하지 않고 차분하게 풀어나가요.", 2, OptionType.KEYWORD),
                    new OptionItem("문제 해결을 위해 GPT등의 자원을 적극적으로 활용해요.", 3, OptionType.KEYWORD),
                    new OptionItem("문제를 해결한 뒤에도 재발 방지를 위한 노력을 기울여요. (예: 문서화, 테스트 케이스 추가 등)", 4, OptionType.KEYWORD),
                    new OptionItem("문제의 원인을 적극적으로 탐구하고 해결해요. (예: 디버깅 툴의 적극적 활용 등)", 5, OptionType.KEYWORD),
                    new OptionItem("어려운 문제를 만나도 피하지 않고 도전해요.", 6, OptionType.KEYWORD),
                    new OptionItem("문제를 해결하기 위해 타인과 의사소통을 할 수 있어요. (예: 팀원과 이슈 공유, 문제 상황 설명 등)", 7, OptionType.KEYWORD),
                    new OptionItem("문제 원인과 해결책에 대한 가설을 세우고 직접 실험해봐요.", 8, OptionType.KEYWORD)
                ),
                KEYWORD_CHECKBOX_MIN_COUNT,
                KEYWORD_CHECKBOX_MAX_COUNT
        );
        Question problemSolvingSectionQuestion = new Question(true, QuestionType.CHECKBOX, problemSolvingOptionGroup, "문제해결 능력에서 어느 부분이 인상 깊었는지 선택해주세요.", "상황을 자세하게 기록할수록 ${revieweeName}에게 도움이 돼요. ${revieweeName} 덕분에 팀이 어떤 문제 상황을 만났을 때, 어떻게 해결했는지 떠올려 보세요.", 1);
        Question problemSolvingSectionTextQuestion = new Question(true, QuestionType.TEXT, CATEGORY_TEXT_QUESTION, "상황을 자세하게 기록할수록 ${revieweeName}에게 도움이 돼요. 어떤 문제 상황이 발생했고, ${revieweeName/가:이} 어떻게 해결했는지 그 과정을 떠올려 보세요.", 2);
        Section problemSolvingSection = new Section(VisibleType.CONDITIONAL, List.of(problemSolvingSectionQuestion, problemSolvingSectionTextQuestion), problemSolvingOptionItem, "문제해결 능력", CATEGORY_HEADER, 3);

        // 시간 관리 능력 섹션
        OptionGroup timeManagingOptionGroup = new OptionGroup(
                List.of(
                    new OptionItem("프로젝트의 일정과 주요 마일스톤을 설정하여 체계적으로 일정을 관리해요.", 1, OptionType.KEYWORD),
                    new OptionItem("일정에 따라 마감 기한을 잘 지켜요.", 2, OptionType.KEYWORD),
                    new OptionItem("업무의 중요도와 긴급성을 고려하여 우선 순위를 정하고, 그에 따라 작업을 분배해요.", 3, OptionType.KEYWORD),
                    new OptionItem("예기치 않은 일정 변경에도 유연하게 대처해요.", 4, OptionType.KEYWORD),
                    new OptionItem("회의 시간과 같은 약속된 시간을 잘 지켜요.", 5, OptionType.KEYWORD)
                ),
                KEYWORD_CHECKBOX_MIN_COUNT,
                KEYWORD_CHECKBOX_MAX_COUNT
        );
        Question timeManagingSectionQuestion = new Question(true, QuestionType.CHECKBOX, timeManagingOptionGroup, "시간 관리 능력에서 어느 부분이 인상 깊었는지 선택해주세요.", null, 1);
        Question timeManagingSectionTextQuestion = new Question(true, QuestionType.TEXT, CATEGORY_TEXT_QUESTION, "상황을 자세하게 기록할수록 ${revieweeName}에게 도움이 돼요. ${revieweeName} 덕분에 팀이 효율적으로 시간관리를 할 수 있었는지 떠올려 보세요.", 2);
        Section timeManagingSection = new Section(VisibleType.CONDITIONAL, List.of(timeManagingSectionQuestion, timeManagingSectionTextQuestion), timeManagementOptionItem, "시간 관리 능력", CATEGORY_HEADER, 4);

        // 기술 역량 섹션
        OptionGroup technicalOptionGroup = new OptionGroup(
                List.of(
                    new OptionItem("관련 언어 / 라이브러리 / 프레임워크 지식이 풍부해요.", 1, OptionType.KEYWORD),
                    new OptionItem("인프라 지식이 풍부해요.", 2, OptionType.KEYWORD),
                    new OptionItem("CS 지식이 풍부해요.", 3, OptionType.KEYWORD),
                    new OptionItem("코드 리뷰에서 중요한 개선점을 제안했어요.", 4, OptionType.KEYWORD),
                    new OptionItem("리팩토링을 통해 전체 코드의 품질을 향상시켰어요.", 5, OptionType.KEYWORD),
                    new OptionItem("복잡한 버그를 신속하게 찾고 해결했어요.", 6, OptionType.KEYWORD),
                    new OptionItem("꼼꼼하게 테스트를 작성했어요.", 7, OptionType.KEYWORD),
                    new OptionItem("처음 보는 기술을 빠르게 습득하여 팀 프로젝트에 적용했어요.", 8, OptionType.KEYWORD),
                    new OptionItem("명확하고 자세한 기술 문서를 작성하여 팀의 이해를 도왔어요.", 9, OptionType.KEYWORD),
                    new OptionItem("컨벤션을 잘 지키면서 클린 코드를 작성하려고 노력했어요.", 10, OptionType.KEYWORD),
                    new OptionItem("성능 최적화에 기여했어요.", 11, OptionType.KEYWORD),
                    new OptionItem("지속적인 학습과 공유를 통해 팀의 기술 수준을 높였어요.", 12, OptionType.KEYWORD)
                ),
                KEYWORD_CHECKBOX_MIN_COUNT,
                KEYWORD_CHECKBOX_MAX_COUNT
        );
        Question technicalSectionQuestion = new Question(true, QuestionType.CHECKBOX, technicalOptionGroup, "기술 역량, 전문 지식에서 어떤 부분이 인상 깊었는지 선택해주세요.", null, 1);
        Question technicalSectionTextQuestion = new Question(true, QuestionType.TEXT, CATEGORY_TEXT_QUESTION, "상황을 자세하게 기록할수록 ${revieweeName}에게 도움이 돼요. ${revieweeName} 덕분에 기술적 역량, 전문 지식적으로 도움을 받은 경험을 떠올려 보세요.", 2);
        Section technicalSection = new Section(VisibleType.CONDITIONAL, List.of(technicalSectionQuestion, technicalSectionTextQuestion), technicalOptionItem, "기술 역량, 전문 지식", CATEGORY_HEADER, 5);

        // 성장 마인드셋 섹션
        OptionGroup mindsetOptionGroup = new OptionGroup(
                List.of(
                    new OptionItem("어려운 상황에도 긍정적인 태도로 임했어요.", 1, OptionType.KEYWORD),
                    new OptionItem("주변 사람들한테 질문하는 것을 부끄러워하지 않았어요.", 2, OptionType.KEYWORD),
                    new OptionItem("어려움이 있어도 끝까지 해냈어요.", 3, OptionType.KEYWORD),
                    new OptionItem("함께 성장하기 위해, 배운 내용을 다른 사람과 공유했어요.", 4, OptionType.KEYWORD),
                    new OptionItem("새로운 것을 두려워하지 않고 적극적으로 배웠어요.", 5, OptionType.KEYWORD),
                    new OptionItem("이론적 학습에서 그치지 않고 직접 적용하려 노력했어요.", 6, OptionType.KEYWORD),
                    new OptionItem("다른 사람들과 비교하지 않고 본인만의 속도로 성장하는 법을 알고 있었어요.", 7, OptionType.KEYWORD),
                    new OptionItem("받은 피드백을 빠르게 수용했어요.", 8, OptionType.KEYWORD),
                    new OptionItem("회고를 통해 성장할 수 있는 방법을 스스로 탐색했어요.", 9, OptionType.KEYWORD),
                    new OptionItem("새로운 아이디어를 시도하고, 기존의 틀을 깨는 것을 두려워하지 않았어요.", 10, OptionType.KEYWORD)
                ),
                KEYWORD_CHECKBOX_MIN_COUNT,
                KEYWORD_CHECKBOX_MAX_COUNT
        );
        Question mindsetSectionQuestion = new Question(true, QuestionType.CHECKBOX, mindsetOptionGroup, "성장 마인드셋에서 어느 부분이 인상 깊었는지 선택해주세요.", null, 1);
        Question mindsetSectionTextQuestion = new Question(true, QuestionType.TEXT, CATEGORY_TEXT_QUESTION, "상황을 자세하게 기록할수록 ${revieweeName}에게 도움이 돼요. 인상깊었던 ${revieweeName}의 성장 마인드셋을 떠올려 보세요.", 2);
        Section mindsetSection = new Section(VisibleType.CONDITIONAL, List.of(mindsetSectionQuestion, mindsetSectionTextQuestion), mindsetOptionItem, "성장 마인드셋", CATEGORY_HEADER, 6);

        // 성장 목표 설정 섹션
        Question growthTargetSectionQuestion = new Question(true, QuestionType.TEXT, "앞으로의 성장을 위해서 ${revieweeName/가:이} 어떤 목표를 설정하면 좋을까요?", "어떤 점을 보완하면 좋을지와 함께 '이렇게 해보면 어떨까?'하는 간단한 솔루션을 제안해봐요.", 1);
        Section growthTargetSection = new Section(VisibleType.ALWAYS, List.of(growthTargetSectionQuestion), null, "보완할 점", "${revieweeName}의 성장을 도와주세요!", 7);

        // 응원의 말 섹션
        Question cheerUpSectionQuestion = new Question(false, QuestionType.TEXT, "${revieweeName}에게 전하고 싶은 다른 리뷰가 있거나 응원의 말이 있다면 적어주세요.", null, 1);
        Section cheerUpSection = new Section(VisibleType.ALWAYS, List.of(cheerUpSectionQuestion), null, "추가 리뷰/응원", "리뷰를 더 하고 싶은 리뷰어를 위한 추가 리뷰!", 8);

        Template template = new Template(
                List.of(categorySection, communicationSection, problemSolvingSection, timeManagingSection,
                        technicalSection, mindsetSection, growthTargetSection, cheerUpSection)
        );
        templateRepository.save(template);
    }
}
