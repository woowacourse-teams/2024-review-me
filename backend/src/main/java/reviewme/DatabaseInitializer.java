package reviewme;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reviewme.question.domain.OptionGroup;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.OptionType;
import reviewme.question.domain.Question2;
import reviewme.question.domain.QuestionType;
import reviewme.question.repository.OptionGroupRepository;
import reviewme.question.repository.OptionItemRepository;
import reviewme.review.repository.QuestionRepository2;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;
import reviewme.template.domain.VisibleType;
import reviewme.template.repository.SectionRepository;
import reviewme.template.repository.TemplateRepository;

@Profile({"local", "dev"})
@Component
@RequiredArgsConstructor
public class DatabaseInitializer {

    public static final String CATEGORY_HEADER = "이제, 선택한 순간들을 바탕으로 {revieweeName}에 대한 리뷰를 작성해볼게요.";
    public static final String CATEGORY_TEXT_QUESTION = "위에서 선택한 사항에 대해 조금 더 자세히 설명해주세요.";

    private final QuestionRepository2 questionRepository;
    private final OptionItemRepository optionItemRepository;
    private final OptionGroupRepository optionGroupRepository;
    private final SectionRepository sectionRepository;
    private final TemplateRepository templateRepository;

    @PostConstruct
    @Transactional
    void setup() {
        // 카테고리 선택 섹션
        long categoryQuestionId = questionRepository.save(new Question2(true, QuestionType.CHECKBOX, "프로젝트 기간 동안, {revieweeName}의 강점이 드러났던 순간을 선택해주세요.", null, 1)).getId();
        long categorySectionId = sectionRepository.save(new Section(VisibleType.ALWAYS, List.of(categoryQuestionId), null, "{revieweeName}와 함께 한 기억을 떠올려볼게요.", 1)).getId();
        long categoryOptionGroupId = optionGroupRepository.save(new OptionGroup(categoryQuestionId, 1, 2)).getId();
        long communicationOptionId = optionItemRepository.save(new OptionItem("🗣️커뮤니케이션, 협업 능력  (ex: 팀원간의 원활한 정보 공유, 명확한 의사소통)", categoryOptionGroupId, 1, OptionType.CATEGORY)).getId();
        long problemSolvingOptionId = optionItemRepository.save(new OptionItem("💡문제 해결 능력  (ex: 프로젝트 중 만난 버그/오류를 분석하고 이를 해결하는 능력)",categoryOptionGroupId,2, OptionType.CATEGORY )).getId();
        long timeManagingOptionId = optionItemRepository.save(new OptionItem("⏰시간 관리 능력 (ex: 일정과 마감 기한 준수, 업무의 우선 순위 분배)",categoryOptionGroupId,3, OptionType.CATEGORY )).getId();
        long technicalOptionId = optionItemRepository.save(new OptionItem("💻기술적 역량, 전문 지식 (ex: 요구 사항을 이해하고 이를 구현하는 능력)",categoryOptionGroupId,4, OptionType.CATEGORY )).getId();
        long growthOptionId = optionItemRepository.save(new OptionItem("🌱성장 마인드셋 (ex: 새로운 분야나 잘 모르는 분야에 도전하는 마음, 꾸준한 노력으로 프로젝트 이전보다 성장하는 모습)",categoryOptionGroupId,5, OptionType.CATEGORY )).getId();

        // 커뮤니케이션 능력 섹션
        long checkBoxCommunicationQuestionId = questionRepository.save(new Question2(true, QuestionType.CHECKBOX, "커뮤니케이션, 협업 능력에서 어떤 부분이 인상 깊었는지 선택해주세요.", null, 1)).getId();
        long textCommunicationQuestionId = questionRepository.save(new Question2(true, QuestionType.TEXT, CATEGORY_TEXT_QUESTION, "상황을 자세하게 기록할수록 {revieweeName}에게 도움이 돼요. {revieweeName} 덕분에 팀이 원활한 소통을 이뤘거나, 함께 일하면서 배울 점이 있었는지 떠올려 보세요.", 2)).getId();
        long communicationSectionId = sectionRepository.save(new Section(VisibleType.CONDITIONAL, List.of(checkBoxCommunicationQuestionId, textCommunicationQuestionId), communicationOptionId, CATEGORY_HEADER, 2)).getId();
        long communicationOptionGroupId = optionGroupRepository.save(new OptionGroup(checkBoxCommunicationQuestionId, 1, 7)).getId();
        optionItemRepository.save(new OptionItem("반대 의견을 내더라도 듣는 사람이 기분 나쁘지 않게 이야기해요.",communicationOptionGroupId,1, OptionType.KEYWORD ));
        optionItemRepository.save(new OptionItem("팀원들의 의견을 잘 모아서 회의가 매끄럽게 진행되도록 해요.",communicationOptionGroupId,2, OptionType.KEYWORD ));
        optionItemRepository.save(new OptionItem("팀의 분위기를 주도해요.",communicationOptionGroupId,3, OptionType.KEYWORD ));
        optionItemRepository.save(new OptionItem("주장을 이야기할 때에는 합당한 근거가 뒤따라요.",communicationOptionGroupId,4, OptionType.KEYWORD ));
        optionItemRepository.save(new OptionItem("팀에게 필요한 것과 그렇지 않은 것을 잘 구분해요.",communicationOptionGroupId,5, OptionType.KEYWORD ));
        optionItemRepository.save(new OptionItem("팀 내 주어진 요구사항에 우선순위를 잘 매겨요. (커뮤니케이션 능력을 특화하자)",communicationOptionGroupId,6, OptionType.KEYWORD ));
        optionItemRepository.save(new OptionItem("서로 다른 분야간의 소통도 중요하게 생각해요.",communicationOptionGroupId,7, OptionType.KEYWORD ));

        // 문제해결 능력 섹션
        long checkBoxProblemSolvingQuestionId = questionRepository.save(new Question2(true, QuestionType.CHECKBOX, "문제해결 능력에서 어느 부분이 인상 깊었는지 선택해주세요.", null, 1)).getId();
        long textProblemSolvingQuestionId = questionRepository.save(new Question2(true, QuestionType.TEXT, CATEGORY_TEXT_QUESTION, "상황을 자세하게 기록할수록 {revieweeName}에게 도움이 돼요.  어떤 문제 상황이 발생했고, {revieweeName}이/가 어떻게 해결했는지 그 과정을 떠올려 보세요.", 2)).getId();
        long problemSolvingSectionId = sectionRepository.save(new Section(VisibleType.CONDITIONAL, List.of(checkBoxProblemSolvingQuestionId, textProblemSolvingQuestionId), problemSolvingOptionId, CATEGORY_HEADER, 3)).getId();
        long problemSolvingOptionGroupId = optionGroupRepository.save(new OptionGroup(checkBoxProblemSolvingQuestionId, 1, 8)).getId();
        optionItemRepository.save(new OptionItem("큰 문제를 작은 단위로 쪼개서 단계별로 해결해나가요.",problemSolvingOptionGroupId,1, OptionType.KEYWORD ));
        optionItemRepository.save(new OptionItem("낯선 문제를 만나도 당황하지 않고 차분하게 풀어나가요.",problemSolvingOptionGroupId,2, OptionType.KEYWORD ));
        optionItemRepository.save(new OptionItem("문제 해결을 위해 GPT등의 자원을 적극적으로 활용해요.",problemSolvingOptionGroupId,3, OptionType.KEYWORD ));
        optionItemRepository.save(new OptionItem("문제를 해결한 뒤에도 재발 방지를 위한 노력을 기울여요. (예: 문서화, 테스트 케이스 추가 등)",problemSolvingOptionGroupId,4, OptionType.KEYWORD ));
        optionItemRepository.save(new OptionItem("문제의 원인을 적극적으로 탐구하고 해결해요. (예: 디버깅 툴의 적극적 활용 등)",problemSolvingOptionGroupId,5, OptionType.KEYWORD ));
        optionItemRepository.save(new OptionItem("어려운 문제를 만나도 피하지 않고 도전해요.",problemSolvingOptionGroupId,6, OptionType.KEYWORD ));
        optionItemRepository.save(new OptionItem("문제를 해결하기 위해 타인과 의사소통을 할 수 있어요. (예: 팀원과 이슈 공유, 문제 상황 설명 등)",problemSolvingOptionGroupId,7, OptionType.KEYWORD ));
        optionItemRepository.save(new OptionItem("문제 원인과 해결책에 대한 가설을 세우고 직접 실험해봐요.",problemSolvingOptionGroupId,8, OptionType.KEYWORD ));

        // 시간 관리 능력 섹션
        long checkBoxTimeManagingQuestionId = questionRepository.save(new Question2(true, QuestionType.CHECKBOX, "시간 관리 능력에서 어느 부분이 인상 깊었는지 선택해주세요.", null, 1)).getId();
        long textTimeManagingQuestionId = questionRepository.save(new Question2(true, QuestionType.TEXT, CATEGORY_TEXT_QUESTION, "상황을 자세하게 기록할수록 {revieweeName}에게 도움이 돼요. {revieweeName} 덕분에 팀이 효율적으로 시간관리를 할 수 있었는지 떠올려 보세요.", 2)).getId();
        long timeManagingSectionId = sectionRepository.save(new Section(VisibleType.CONDITIONAL, List.of(checkBoxTimeManagingQuestionId, textTimeManagingQuestionId), timeManagingOptionId, CATEGORY_HEADER, 4)).getId();
        long timeManagingOptionGroupId = optionGroupRepository.save(new OptionGroup(checkBoxTimeManagingQuestionId, 1, 5)).getId();
        optionItemRepository.save(new OptionItem("프로젝트의 일정과 주요 마일스톤을 설정하여 체계적으로 일정을 관리해요.",timeManagingOptionGroupId,1, OptionType.KEYWORD ));
        optionItemRepository.save(new OptionItem("일정에 따라 마감 기한을 잘 지켜요.",timeManagingOptionGroupId,2, OptionType.KEYWORD ));
        optionItemRepository.save(new OptionItem("업무의 중요도와 긴급성을 고려하여 우선 순위를 정하고, 그에 따라 작업을 분배해요.",timeManagingOptionGroupId,3, OptionType.KEYWORD ));
        optionItemRepository.save(new OptionItem("예기치 않은 일정 변경에도 유연하게 대처해요.",timeManagingOptionGroupId,4, OptionType.KEYWORD ));
        optionItemRepository.save(new OptionItem("회의 시간과 같은 약속된 시간을 잘 지켜요.",timeManagingOptionGroupId,5, OptionType.KEYWORD ));

        // 기술 역량 섹션
        long checkBoxTechnicalQuestionId = questionRepository.save(new Question2(true, QuestionType.CHECKBOX, "기술 역량, 전문 지식에서 어떤 부분이 인상 깊었는지 선택해주세요.", null, 1)).getId();
        long textTechnicalQuestionId = questionRepository.save(new Question2(true, QuestionType.TEXT, CATEGORY_TEXT_QUESTION, "상황을 자세하게 기록할수록 {revieweeName}에게 도움이 돼요. {revieweeName} 덕분에 기술적 역량, 전문 지식적으로 도움을 받은 경험을 떠올려 보세요.", 2)).getId();
        long technicalSectionId = sectionRepository.save(new Section(VisibleType.CONDITIONAL, List.of(checkBoxTechnicalQuestionId, textTechnicalQuestionId), technicalOptionId, CATEGORY_HEADER, 5)).getId();
        long technicalOptionGroupId = optionGroupRepository.save(new OptionGroup(checkBoxTechnicalQuestionId, 1, 12)).getId();
        optionItemRepository.save(new OptionItem("관련 언어 / 라이브러리 / 프레임워크 지식이 풍부해요.",technicalOptionGroupId,1, OptionType.KEYWORD ));
        optionItemRepository.save(new OptionItem("인프라 지식이 풍부해요.",technicalOptionGroupId,2, OptionType.KEYWORD ));
        optionItemRepository.save(new OptionItem("CS 지식이 풍부해요.",technicalOptionGroupId,3, OptionType.KEYWORD ));
        optionItemRepository.save(new OptionItem("코드 리뷰에서 중요한 개선점을 제안했어요.",technicalOptionGroupId,4, OptionType.KEYWORD ));
        optionItemRepository.save(new OptionItem("리팩토링을 통해 전체 코드의 품질을 향상시켰어요.",technicalOptionGroupId,5, OptionType.KEYWORD ));
        optionItemRepository.save(new OptionItem("복잡한 버그를 신속하게 찾고 해결했어요.",technicalOptionGroupId,6, OptionType.KEYWORD ));
        optionItemRepository.save(new OptionItem("꼼꼼하게 테스트를 작성했어요.",technicalOptionGroupId,7, OptionType.KEYWORD ));
        optionItemRepository.save(new OptionItem("처음 보는 기술을 빠르게 습득하여 팀 프로젝트에 적용했어요.",technicalOptionGroupId,8, OptionType.KEYWORD ));
        optionItemRepository.save(new OptionItem("명확하고 자세한 기술 문서를 작성하여 팀의 이해를 도왔어요.",technicalOptionGroupId,9, OptionType.KEYWORD ));
        optionItemRepository.save(new OptionItem("컨벤션을 잘 지키면서 클린 코드를 작성하려고 노력했어요.",technicalOptionGroupId,10, OptionType.KEYWORD ));
        optionItemRepository.save(new OptionItem("성능 최적화에 기여했어요.",technicalOptionGroupId,11, OptionType.KEYWORD ));
        optionItemRepository.save(new OptionItem("지속적인 학습과 공유를 통해 팀의 기술 수준을 높였어요.",technicalOptionGroupId,12, OptionType.KEYWORD ));

        // 성장 마인드셋 섹션
        long checkBoxGrowthQuestionId = questionRepository.save(new Question2(true, QuestionType.CHECKBOX, "성장 마인드셋에서 어떤 부분이 인상 깊었는지 선택해주세요.", null, 1)).getId();
        long textGrowthQuestionId = questionRepository.save(new Question2(true, QuestionType.TEXT, CATEGORY_TEXT_QUESTION, "상황을 자세하게 기록할수록 {revieweeName}에게 도움이 돼요. 인상깊었던 {revieweeName}의 성장 마인드셋을 떠올려 보세요.", 2)).getId();
        long growthSectionId = sectionRepository.save(new Section(VisibleType.CONDITIONAL, List.of(checkBoxGrowthQuestionId, textGrowthQuestionId), growthOptionId, CATEGORY_HEADER, 6)).getId();
        long growthOptionGroupId = optionGroupRepository.save(new OptionGroup(checkBoxGrowthQuestionId, 1, 10)).getId();
        optionItemRepository.save(new OptionItem("어떤 상황에도 긍정적인 태도로 임해요.",growthOptionGroupId,1, OptionType.KEYWORD ));
        optionItemRepository.save(new OptionItem("주변 사람들한테 질문하는 것을 부끄러워하지 않아요.",growthOptionGroupId,2, OptionType.KEYWORD ));
        optionItemRepository.save(new OptionItem("어려움이 있어도 끝까지 해내요.",growthOptionGroupId,3, OptionType.KEYWORD ));
        optionItemRepository.save(new OptionItem("함께 성장하기 위해, 배운 내용을 다른 사람과 공유해요.",growthOptionGroupId,4, OptionType.KEYWORD ));
        optionItemRepository.save(new OptionItem("새로운 것을 두려워하지 않고 적극적으로 배워나가요.",growthOptionGroupId,5, OptionType.KEYWORD ));
        optionItemRepository.save(new OptionItem("이론적 학습에서 그치지 않고 직접 적용하려 노력해요.",growthOptionGroupId,6, OptionType.KEYWORD ));
        optionItemRepository.save(new OptionItem("다른 사람들과 비교하지 않고 본인만의 속도로 성장하는 법을 알고 있어요.",growthOptionGroupId,7, OptionType.KEYWORD ));
        optionItemRepository.save(new OptionItem("받은 피드백을 빠르게 수용해요.",growthOptionGroupId,8, OptionType.KEYWORD ));
        optionItemRepository.save(new OptionItem("회고를 통해 성장할 수 있는 방법을 스스로 탐색해요.",growthOptionGroupId,9, OptionType.KEYWORD ));
        optionItemRepository.save(new OptionItem("새로운 아이디어를 시도하고, 기존의 틀을 깨는 것을 두려워하지 않아요.",growthOptionGroupId,10, OptionType.KEYWORD ));

        // 성장 목표 설정 섹션
        long textGrowthGoalQuestionId = questionRepository.save(new Question2(true, QuestionType.TEXT, "앞으로의 성장을 위해서 {revieweeName}이/가 어떤 목표를 설정하면 좋을까요?", "어떤 점을 보완하면 좋을지와 함께 '이렇게 해보면 어떨까?'하는 간단한 솔루션을 제안해봐요.", 1)).getId();
        long textGrowthGoalSectionId = sectionRepository.save(new Section(VisibleType.ALWAYS, List.of(textGrowthGoalQuestionId), null, "{revieweeName}의 성장을 도와주세요!", 7)).getId();

        // 응원의 말 섹션
        long textCheerUpQuestionId = questionRepository.save(new Question2(false, QuestionType.TEXT, "{revieweeName}에게 전하고 싶은 다른 리뷰가 있거나 응원의 말이 있다면 적어주세요.", null, 1)).getId();
        long cheerUpSectionId = sectionRepository.save(new Section(VisibleType.ALWAYS, List.of(textCheerUpQuestionId), null, "리뷰를 더 하고 싶은 리뷰어를 위한 추가 리뷰!", 8)).getId();

        templateRepository.save(new Template(List.of(
                categorySectionId,
                communicationSectionId,
                problemSolvingSectionId,
                timeManagingSectionId,
                technicalSectionId,
                growthSectionId,
                textGrowthGoalSectionId,
                cheerUpSectionId
        )));
    }
}
