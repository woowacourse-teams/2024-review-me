package reviewme.security.aspect;

import static org.assertj.core.api.Assertions.assertThatCode;
import static reviewme.fixture.MemberFixture.회원;
import static reviewme.fixture.ReviewFixture.비회원_작성_리뷰;
import static reviewme.fixture.ReviewFixture.회원_작성_리뷰;
import static reviewme.fixture.ReviewGroupFixture.비회원_리뷰_그룹;
import static reviewme.fixture.ReviewGroupFixture.회원_지정_리뷰_그룹;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import reviewme.auth.domain.GitHubMember;
import reviewme.member.domain.Member;
import reviewme.member.repository.MemberRepository;
import reviewme.review.domain.Review;
import reviewme.review.repository.ReviewRepository;
import reviewme.review.service.exception.ReviewNotFoundException;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.security.aspect.exception.ForbiddenReviewAccessException;
import reviewme.security.session.SessionManager;
import reviewme.support.ServiceTest;

@ServiceTest
class ReviewAuthorizationAspectTest {

    @Autowired
    private AopTestClass aopTestClass;

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewGroupRepository reviewGroupRepository;

    @Autowired
    private MemberRepository memberRepository;

    MockHttpServletRequest request;
    MockHttpSession session;

    @BeforeEach
    void setUp() {
        request = new MockHttpServletRequest();
        session = new MockHttpSession();
        request.setSession(session);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    void 존재하지_않는_리뷰에_접근하면_NotFound_예외가_발생한다() {
        // when & then
        assertThatCode(() -> aopTestClass.testReviewMethod(1L))
                .isInstanceOf(ReviewNotFoundException.class);
    }

    @Nested
    class 성공적으로_리뷰에_접근할_수_있다 {

        @Test
        void 회원은_자신이_작성한_리뷰에_접근할_수_있다() {
            // given
            Member member = memberRepository.save(회원());
            Review review = reviewRepository.save(회원_작성_리뷰(member.getId(), 1L, 1L, List.of()));

            GitHubMember gitHubMember = new GitHubMember(member.getId(), "name", "avatarUrl");
            sessionManager.saveGitHubMember(request, gitHubMember);

            // when & then
            assertThatCode(() -> aopTestClass.testReviewMethod(review.getId()))
                    .doesNotThrowAnyException();
        }

        @Test
        void 회원은_자신이_만든_리뷰_그룹에_작성된_리뷰에_접근할_수_있다() {
            // given
            Member member = memberRepository.save(회원());
            ReviewGroup reviewGroup = reviewGroupRepository.save(회원_지정_리뷰_그룹(member.getId()));
            Review review = reviewRepository.save(비회원_작성_리뷰(1L, reviewGroup.getId(), List.of()));

            GitHubMember gitHubMember = new GitHubMember(member.getId(), "name", "avatarUrl");
            sessionManager.saveGitHubMember(request, gitHubMember);

            // when & then
            assertThatCode(() -> aopTestClass.testReviewMethod(review.getId()))
                    .doesNotThrowAnyException();
        }

        @Test
        void 리뷰_그룹을_인증한_비회원은_리뷰_그룹에_작성된_리뷰에_접근할_수_있다() {
            // given
            ReviewGroup reviewGroup = reviewGroupRepository.save(비회원_리뷰_그룹());
            Review review = reviewRepository.save(비회원_작성_리뷰(1L, reviewGroup.getId(), List.of()));
            sessionManager.saveReviewRequestCode(request, reviewGroup.getReviewRequestCode());

            // when & then
            assertThatCode(() -> aopTestClass.testReviewMethod(review.getId()))
                    .doesNotThrowAnyException();
        }
    }

    @Nested
    class 유효하지_않은_세션으로_접근하면_예외가_발생한다 {

        @Test
        void 세션이_존재하지_않으면_Unauthorized_예외가_발생한다() {
            // given
            request.setSession(null);
            Review review = reviewRepository.save(비회원_작성_리뷰(1L, 1L, List.of()));

            // when & then
            assertThatCode(() -> aopTestClass.testReviewMethod(review.getId()))
                    .isInstanceOf(ForbiddenReviewAccessException.class);
        }

        @Test
        void 세션에_저장된_정보가_없으면_Unauthorized_예외가_발생한다() {
            // given
            Review review = reviewRepository.save(비회원_작성_리뷰(1L, 1L, List.of()));

            // when & then
            assertThatCode(() -> aopTestClass.testReviewMethod(review.getId()))
                    .isInstanceOf(ForbiddenReviewAccessException.class);
        }
    }

    @Nested
    class 회원이_리뷰에_접근할_수_없으면_예외가_발생한다 {

        @Test
        void 자신이_작성하지_않은_리뷰에_접근하면_Unauthorized_예외가_발생한다() {
            // given
            Review review = reviewRepository.save(비회원_작성_리뷰(1L, 1L, List.of()));

            GitHubMember gitHubMember = new GitHubMember(1L, "name", "avatarUrl");
            sessionManager.saveGitHubMember(request, gitHubMember);

            // when & then
            assertThatCode(() -> aopTestClass.testReviewMethod(review.getId()))
                    .isInstanceOf(ForbiddenReviewAccessException.class);
        }

        @Test
        void 자신이_만들지_않은_리뷰그룹의_리뷰에_접근하면_Unauthorized_예외가_발생한다() {
            // given
            Member other = memberRepository.save(회원("email123@test.com"));
            ReviewGroup othersReviewGroup = reviewGroupRepository.save(회원_지정_리뷰_그룹(other.getId()));
            Review review = reviewRepository.save(비회원_작성_리뷰(1L, othersReviewGroup.getId(), List.of()));

            Member member = memberRepository.save(회원("email321@test.com"));
            GitHubMember gitHubMember = new GitHubMember(member.getId(), "name", "avatarUrl");
            sessionManager.saveGitHubMember(request, gitHubMember);

            // when & then
            assertThatCode(() -> aopTestClass.testReviewMethod(review.getId()))
                    .isInstanceOf(ForbiddenReviewAccessException.class);
        }
    }

    @Test
    void 비회원이_리뷰에_접근할_수_없으면_예외가_발생한다() {
        // given
        ReviewGroup othersReviewGroup = reviewGroupRepository.save(비회원_리뷰_그룹("abcd", "efgh"));
        Review review = reviewRepository.save(비회원_작성_리뷰(1L, othersReviewGroup.getId(), List.of()));

        ReviewGroup reviewGroup = reviewGroupRepository.save(비회원_리뷰_그룹("1234", "5678"));
        sessionManager.saveReviewRequestCode(request, reviewGroup.getReviewRequestCode());

        // when & then
        assertThatCode(() -> aopTestClass.testReviewMethod(review.getId()))
                .isInstanceOf(ForbiddenReviewAccessException.class);
    }
}
