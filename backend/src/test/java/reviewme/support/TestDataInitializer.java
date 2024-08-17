package reviewme.support;

import static reviewme.fixture.OptionGroupFixture.꼬리_질문_선택지_그룹;
import static reviewme.fixture.OptionGroupFixture.카테고리_선택지_그룹;
import static reviewme.fixture.OptionItemFixture.카테고리_선택지;
import static reviewme.fixture.QuestionFixture.꼬리_질문_서술형;
import static reviewme.fixture.QuestionFixture.꼬리_질문_선택형;
import static reviewme.fixture.QuestionFixture.단점_보완_질문_서술형;
import static reviewme.fixture.QuestionFixture.응원_서술형;
import static reviewme.fixture.QuestionFixture.카테고리_질문_선택형;
import static reviewme.fixture.SectionFixture.꼬리_질문_섹션;
import static reviewme.fixture.SectionFixture.단점_보완_섹션;
import static reviewme.fixture.SectionFixture.카테고리_섹션;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import reviewme.question.domain.OptionGroup;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.Question;
import reviewme.question.repository.OptionGroupRepository;
import reviewme.question.repository.OptionItemRepository;
import reviewme.question.repository.QuestionRepository;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;
import reviewme.template.repository.SectionRepository;
import reviewme.template.repository.TemplateRepository;

@TestComponent
public class TestDataInitializer {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private OptionGroupRepository optionGroupRepository;

    @Autowired
    private OptionItemRepository optionItemRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private TemplateRepository templateRepository;

    public static Question 저장된_카테고리_질문_선택형;
    public static Question 저장된_꼬리_질문_선택형1;
    public static Question 저장된_꼬리_질문_서술형1;
    public static Question 저장된_꼬리_질문_선택형2;
    public static Question 저장된_꼬리_질문_서술형2;
    public static Question 저장된_단점_보완_질문_서술형;
    public static Question 저장된_응원_질문_서술형;

    public static OptionGroup 저장된_카테고리_선택지_그룹;
    public static OptionGroup 저장된_꼬리_질문_선택지_그룹1;
    public static OptionGroup 저장된_꼬리_질문_선택지_그룹2;

    public static OptionItem 저장된_카테고리_선택지1;
    public static OptionItem 저장된_카테고리_선택지2;
    public static OptionItem 저장된_꼬리_질문1_선택지1;
    public static OptionItem 저장된_꼬리_질문1_선택지2;
    public static OptionItem 저장된_꼬리_질문2_선택지1;
    public static OptionItem 저장된_꼬리_질문2_선택지2;

    public static Section 저장된_카테고리_섹션;
    public static Section 저장된_꼬리_질문_섹션1;
    public static Section 저장된_꼬리_질문_섹션2;
    public static Section 저장된_단점_보완_섹션;
    public static Section 저장된_응원_질문_섹션;

    public static Template 저장된_템플릿;

    public void saveTemplateRelatedData() {
        저장된_카테고리_질문_선택형 = questionRepository.save(카테고리_질문_선택형.create());
        저장된_꼬리_질문_선택형1 = questionRepository.save(꼬리_질문_선택형.create());
        저장된_꼬리_질문_서술형1 = questionRepository.save(꼬리_질문_서술형.create());
        저장된_꼬리_질문_선택형2 = questionRepository.save(꼬리_질문_선택형.create());
        저장된_꼬리_질문_서술형2 = questionRepository.save(꼬리_질문_서술형.create());
        저장된_단점_보완_질문_서술형 = questionRepository.save(단점_보완_질문_서술형.create());
        저장된_응원_질문_서술형 = questionRepository.save(응원_서술형.create());

        저장된_카테고리_선택지_그룹 = optionGroupRepository.save(카테고리_선택지_그룹.createWithQuestionId(저장된_카테고리_질문_선택형.getId()));
        저장된_꼬리_질문_선택지_그룹1 = optionGroupRepository.save(꼬리_질문_선택지_그룹.createWithQuestionId(저장된_꼬리_질문_선택형1.getId()));
        저장된_꼬리_질문_선택지_그룹2 = optionGroupRepository.save(꼬리_질문_선택지_그룹.createWithQuestionId(저장된_꼬리_질문_선택형2.getId()));

        저장된_카테고리_선택지1 = optionItemRepository.save(
                카테고리_선택지.createWithOptionGroupIdAndPosition(저장된_카테고리_선택지_그룹.getId(), 1)
        );
        저장된_카테고리_선택지2 = optionItemRepository.save(
                카테고리_선택지.createWithOptionGroupIdAndPosition(저장된_카테고리_선택지_그룹.getId(), 2)
        );
        저장된_꼬리_질문1_선택지1 = optionItemRepository.save(
                카테고리_선택지.createWithOptionGroupIdAndPosition(저장된_꼬리_질문_선택지_그룹1.getId(), 1)
        );
        저장된_꼬리_질문1_선택지2 = optionItemRepository.save(
                카테고리_선택지.createWithOptionGroupIdAndPosition(저장된_꼬리_질문_선택지_그룹1.getId(), 2)
        );
        저장된_꼬리_질문2_선택지1 = optionItemRepository.save(
                카테고리_선택지.createWithOptionGroupIdAndPosition(저장된_꼬리_질문_선택지_그룹2.getId(), 1)
        );
        저장된_꼬리_질문2_선택지2 = optionItemRepository.save(
                카테고리_선택지.createWithOptionGroupIdAndPosition(저장된_꼬리_질문_선택지_그룹2.getId(), 2)
        );

        저장된_카테고리_섹션 = sectionRepository.save(카테고리_섹션.createWithQuestionIds(List.of(저장된_카테고리_질문_선택형.getId())));
        저장된_꼬리_질문_섹션1 = sectionRepository.save(
                꼬리_질문_섹션.createWithQuestionIdsAndOnSelectedOptionId(
                        List.of(저장된_꼬리_질문_선택형1.getId(), 저장된_꼬리_질문_서술형1.getId()), 1L
                )
        );
        저장된_꼬리_질문_섹션2 = sectionRepository.save(
                꼬리_질문_섹션.createWithQuestionIdsAndOnSelectedOptionId(
                        List.of(저장된_꼬리_질문_선택형2.getId(), 저장된_꼬리_질문_서술형2.getId()), 2L
                )
        );
        저장된_단점_보완_섹션 = sectionRepository.save(단점_보완_섹션.createWithQuestionIds(List.of(저장된_단점_보완_질문_서술형.getId())));
        저장된_응원_질문_섹션 = sectionRepository.save(카테고리_섹션.createWithQuestionIds(List.of(저장된_응원_질문_서술형.getId())));

        저장된_템플릿 = templateRepository.save(new Template(List.of(
                        저장된_카테고리_섹션.getId(),
                        저장된_꼬리_질문_섹션1.getId(), 저장된_꼬리_질문_섹션2.getId(),
                        저장된_단점_보완_섹션.getId(), 저장된_응원_질문_섹션.getId())
                )
        );
    }
}
