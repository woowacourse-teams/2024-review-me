package reviewme.global.authorization;

import static org.assertj.core.api.Assertions.assertThatCode;
import static reviewme.fixture.MemberFixture.회원;
import static reviewme.fixture.ReviewGroupFixture.리뷰_그룹;
import static reviewme.fixture.ReviewGroupFixture.회원_지정_리뷰_그룹;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import reviewme.auth.domain.GitHubMember;
import reviewme.global.authorization.exception.UnauthorizedReviewGroupAccessException;
import reviewme.global.session.SessionManager;
import reviewme.member.domain.Member;
import reviewme.member.repository.MemberRepository;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.domain.exception.ReviewGroupNotFoundException;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.support.BaseSpringBootTest;

@BaseSpringBootTest
class ReviewGroupAuthorizationAspectTest {

    @Autowired
    private AopTestClass aopTestClass;

    @Autowired
    private SessionManager sessionManager;

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

    @Nested
    class 리뷰_그룹에_접근할_수_있으면_예외가_발생하지_않는다 {

        @Test
        void 회원이_그룹에_접근할_수_있다() {
            // given
            Member member = memberRepository.save(회원());
            ReviewGroup reviewGroup = reviewGroupRepository.save(회원_지정_리뷰_그룹(member.getId()));
            GitHubMember gitHubMember = new GitHubMember(member.getId(), "name", "avatarUrl");
            sessionManager.saveGitHubMember(session, gitHubMember);

            // when & then
            assertThatCode(() -> aopTestClass.testReviewGroupMethod(reviewGroup.getId())).doesNotThrowAnyException();
        }

        @Test
        void 비회원이_그룹에_접근할_수_있다() {
            // given
            ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹());
            sessionManager.saveReviewRequestCode(session, reviewGroup.getReviewRequestCode());

            // when & then
            assertThatCode(() -> aopTestClass.testReviewGroupMethod(reviewGroup.getId())).doesNotThrowAnyException();
        }
    }

    @Nested
    class 리뷰_그룹에_접근할_수_없으면_예외가_발생한다 {

        @Test
        void 존재하지_않는_리뷰그룹에_접근하면_NotFound_예외가_발생한다() {
            // when & then
            assertThatCode(() -> aopTestClass.testReviewGroupMethod(1L))
                    .isInstanceOf(ReviewGroupNotFoundException.class);
        }

        @Test
        void 세션에_저장된_정보가_없으면_Unauthorized_예외가_발생한다() {
            // given
            request.setSession(null);
            ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹());

            // when & then
            assertThatCode(() -> aopTestClass.testReviewGroupMethod(reviewGroup.getId()))
                    .isInstanceOf(UnauthorizedReviewGroupAccessException.class);
        }

        @Test
        void 다른_회원이_만든_리뷰_그룹에_접근하면_Unauthorized_예외가_발생한다() {
            // given
            Member member = memberRepository.save(회원("email@test.com"));
            ReviewGroup membersReviewGroup = reviewGroupRepository.save(회원_지정_리뷰_그룹(member.getId()));

            Member other = memberRepository.save(회원("email@test.net"));
            GitHubMember gitHubMember = new GitHubMember(other.getId(), "name", "avatarUrl");
            sessionManager.saveGitHubMember(session, gitHubMember);

            // when & then
            assertThatCode(() -> aopTestClass.testReviewGroupMethod(membersReviewGroup.getId()))
                    .isInstanceOf(UnauthorizedReviewGroupAccessException.class);
        }

        @Test
        void 리뷰_요청_코드가_다른_리뷰그룹에_접근하면_Unauthorized_예외가_발생한다() {
            // given
            ReviewGroup group = reviewGroupRepository.save(리뷰_그룹("1111", "2222"));

            ReviewGroup other = reviewGroupRepository.save(리뷰_그룹("3333", "4444"));
            sessionManager.saveReviewRequestCode(session, other.getReviewRequestCode());

            // when & then
            assertThatCode(() -> aopTestClass.testReviewGroupMethod(group.getId()))
                    .isInstanceOf(UnauthorizedReviewGroupAccessException.class);
        }
    }
}
