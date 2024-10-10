package reviewme.review.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static reviewme.fixture.QuestionFixture.서술형_필수_질문;
import static reviewme.fixture.ReviewGroupFixture.리뷰_그룹;
import static reviewme.fixture.SectionFixture.항상_보이는_섹션;
import static reviewme.fixture.TemplateFixture.템플릿;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import reviewme.question.domain.Question;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.domain.Answer;
import reviewme.review.domain.Review;
import reviewme.review.domain.TextAnswer;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;
import reviewme.template.repository.SectionRepository;
import reviewme.template.repository.TemplateRepository;

@DataJpaTest
class AnswerRepositoryTest {

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    SectionRepository sectionRepository;

    @Autowired
    TemplateRepository templateRepository;

    @Autowired
    ReviewGroupRepository reviewGroupRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Test
    void 주어진_질문들에_대한_답변들을_반환한다() {
        // given
        Question question1 = questionRepository.save(서술형_필수_질문());
        Question question2 = questionRepository.save(서술형_필수_질문());
        Question question3 = questionRepository.save(서술형_필수_질문());
        Section section = sectionRepository.save(항상_보이는_섹션(
                List.of(question1.getId(), question2.getId(), question3.getId())));
        Template template = templateRepository.save(템플릿(List.of(section.getId())));
        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹());

        TextAnswer answer1 = new TextAnswer(question1.getId(), "답1".repeat(20));
        TextAnswer answer2 = new TextAnswer(question2.getId(), "답2".repeat(20));
        TextAnswer answer3 = new TextAnswer(question3.getId(), "답3".repeat(20));
        reviewRepository.save(new Review(template.getId(), reviewGroup.getId(), List.of(answer1, answer2, answer3)));

        // when
        List<Answer> actual = answerRepository.findAllByQuestionIds(List.of(question1.getId(), question2.getId()));

        // then
        assertThat(actual).containsOnly(answer1, answer2);
    }

    @Test
    void 리뷰_그룹_id로_리뷰들을_찾아_id를_반환한다() {
        // given
        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹());
        TextAnswer answer1 = new TextAnswer(1L, "text answer1");
        TextAnswer answer2 = new TextAnswer(1L, "text answer2");
        Review review = reviewRepository.save(new Review(1L, reviewGroup.getId(), List.of(answer1, answer2)));

        // when
        Set<Long> actual = answerRepository.findIdsByReviewGroupId(reviewGroup.getId());

        // then
        assertThat(actual).containsExactly(answer1.getId(), answer2.getId());
    }

    @Test
    void 질문_id로_리뷰들을_찾아_id를_반환한다() {
        // given
        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹());
        long questionId1 = questionRepository.save(서술형_필수_질문()).getId();
        long questionId2 = questionRepository.save(서술형_필수_질문()).getId();
        TextAnswer textAnswer1_Q1 = new TextAnswer(questionId1, "text answer1 by Q1");
        TextAnswer textAnswer2_Q1 = new TextAnswer(questionId1, "text answer2 by Q1");
        TextAnswer textAnswer1_Q2 = new TextAnswer(questionId2, "text answer1 by Q2");

        reviewRepository.saveAll(List.of(
                new Review(1L, reviewGroup.getId(), List.of(textAnswer1_Q1, textAnswer2_Q1)),
                new Review(1L, reviewGroup.getId(), List.of(textAnswer1_Q2)
                )));

        // when
        Set<Long> actual = answerRepository.findIdsByQuestionId(questionId1);

        // then
        assertThat(actual).containsExactly(textAnswer1_Q1.getId(), textAnswer2_Q1.getId());
    }
}
