package reviewme.review;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import reviewme.keyword.Keyword;
import reviewme.keyword.KeywordRepository;
import reviewme.member.Member;
import reviewme.member.MemberRepository;
import reviewme.member.ReviewerGroup;
import reviewme.member.ReviewerGroupRepository;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class ReviewServiceTest {

    @Autowired
    ReviewService reviewService;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ReviewerGroupRepository reviewerGroupRepository;

    @Autowired
    KeywordRepository keywordRepository;

    @Test
    void 리뷰를_작성한다() {
        // given
        memberRepository.save(new Member("산초"));
        Member reviewee = memberRepository.save(new Member("아루"));
        reviewerGroupRepository.save(new ReviewerGroup(reviewee, "그룹A", LocalDateTime.of(2024, 1, 1, 1, 1)));
        Keyword keyword1 = keywordRepository.save(new Keyword("꼼꼼해요"));
        Keyword keyword2 = keywordRepository.save(new Keyword("친절해요"));

        CreateReviewContentRequest contentRequest1 = new CreateReviewContentRequest(
                1L, "소프트스킬이 어떤가요?", "소통을 잘해요"
        );
        CreateReviewContentRequest contentRequest2 = new CreateReviewContentRequest(
                2L, "기술역량이 어떤가요?", "스트림을 잘다뤄요"
        );
        CreateReviewRequest createReviewRequest = new CreateReviewRequest(
                1L,
                1L,
                List.of(contentRequest1, contentRequest2),
                List.of(keyword1.getId(), keyword2.getId())
        );

        // when
        reviewService.createReview(createReviewRequest);

        // then
        List<Review> actual = reviewRepository.findAll();
        assertThat(actual).hasSize(1);
    }
}
