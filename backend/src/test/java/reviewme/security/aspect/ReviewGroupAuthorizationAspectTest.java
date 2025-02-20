package reviewme.security.aspect;

import static org.assertj.core.api.Assertions.assertThatCode;
import static reviewme.fixture.MemberFixture.회원;
import static reviewme.fixture.ReviewGroupFixture.비회원_리뷰_그룹;
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
import reviewme.member.domain.Member;
import reviewme.member.repository.MemberRepository;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.reviewgroup.service.exception.ReviewGroupNotFoundByReviewRequestCodeException;
import reviewme.security.aspect.exception.ForbiddenReviewGroupAccessException;
import reviewme.security.session.SessionManager;
import reviewme.support.ServiceTest;

@ServiceTest
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
    class 성공적으로_리뷰_그룹에_접근할_수_있다 {

        @Test
        void 회원은_자신이_만든_리뷰_그룹에_접근할_수_있다() {
            // given
            Member member = memberRepository.save(회원());
            ReviewGroup reviewGroup = reviewGroupRepository.save(회원_지정_리뷰_그룹(member.getId()));
            GitHubMember gitHubMember = new GitHubMember(member.getId(), "name", "avatarUrl");
            sessionManager.saveGitHubMember(request, gitHubMember);

            // when & then
            assertThatCode(() -> aopTestClass.testReviewGroupMethod(reviewGroup.getReviewRequestCode()))
                    .doesNotThrowAnyException();
        }

        @Test
        void 비회원은_자신이_만든_리뷰_그룹에_접근할_수_있다() {
            // given
            ReviewGroup reviewGroup = reviewGroupRepository.save(비회원_리뷰_그룹());
            sessionManager.saveReviewRequestCode(request, reviewGroup.getReviewRequestCode());

            // when & then
            assertThatCode(() -> aopTestClass.testReviewGroupMethod(reviewGroup.getReviewRequestCode()))
                    .doesNotThrowAnyException();
        }
    }

    @Test
    void 존재하지_않는_리뷰_그룹에_접근하면_NotFound_예외가_발생한다() {
        // when & then
        assertThatCode(() -> aopTestClass.testReviewGroupMethod("notExistsReviewRequestCode"))
                .isInstanceOf(ReviewGroupNotFoundByReviewRequestCodeException.class);
    }

    @Nested
    class 유효하지_않은_세션으로_접근하면_예외가_발생한다 {

        @Test
        void 세션이_없으면_Unauthorized_예외가_발생한다() {
            // given
            request.setSession(null);
            ReviewGroup reviewGroup = reviewGroupRepository.save(비회원_리뷰_그룹());

            // when & then
            assertThatCode(() -> aopTestClass.testReviewGroupMethod(reviewGroup.getReviewRequestCode()))
                    .isInstanceOf(ForbiddenReviewGroupAccessException.class);
        }

        @Test
        void 세션에_저장된_정보가_없으면_Unauthorized_예외가_발생한다() {
            // given
            ReviewGroup reviewGroup = reviewGroupRepository.save(비회원_리뷰_그룹());

            // when & then
            assertThatCode(() -> aopTestClass.testReviewGroupMethod(reviewGroup.getReviewRequestCode()))
                    .isInstanceOf(ForbiddenReviewGroupAccessException.class);
        }
    }

    @Test
    void 다른_회원이_만든_리뷰_그룹에_접근하면_Unauthorized_예외가_발생한다() {
        // given
        Member other = memberRepository.save(회원("email456@test.com"));
        ReviewGroup membersReviewGroup = reviewGroupRepository.save(회원_지정_리뷰_그룹(other.getId()));

        Member member = memberRepository.save(회원("email123@test.com"));
        GitHubMember gitHubMember = new GitHubMember(member.getId(), "name", "avatarUrl");
        sessionManager.saveGitHubMember(request, gitHubMember);

        // when & then
        assertThatCode(() -> aopTestClass.testReviewGroupMethod(membersReviewGroup.getReviewRequestCode()))
                .isInstanceOf(ForbiddenReviewGroupAccessException.class);
    }

    @Test
    void 리뷰_요청_코드가_다른_리뷰_그룹에_접근하면_Unauthorized_예외가_발생한다() {
        // given
        ReviewGroup other = reviewGroupRepository.save(비회원_리뷰_그룹("3333", "4444"));
        sessionManager.saveReviewRequestCode(request, other.getReviewRequestCode());

        ReviewGroup group = reviewGroupRepository.save(비회원_리뷰_그룹("1111", "2222"));

        // when & then
        assertThatCode(() -> aopTestClass.testReviewGroupMethod(group.getReviewRequestCode()))
                .isInstanceOf(ForbiddenReviewGroupAccessException.class);
    }
}
