package reviewme.auth.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reviewme.auth.domain.MemberPrincipal;
import reviewme.auth.domain.Principal;
import reviewme.auth.domain.ReviewGroupPrincipal;
import reviewme.member.domain.Member;
import reviewme.reviewgroup.domain.ReviewGroup;

@RequiredArgsConstructor
@Service
public class PrincipalService {

    public boolean canAccessReviewGroup(Principal principal, long reviewGroupId) {
        if (principal instanceof ReviewGroupPrincipal) {
            return ((ReviewGroupPrincipal) principal).getReviewGroup().getId() == reviewGroupId;
        }
        if (principal instanceof MemberPrincipal) {
            // todo: 리뷰 그룹과 사용자 연결
        }
        return false;
    }

    public Optional<Member> getMember(Principal principal) {
        if (principal instanceof MemberPrincipal) {
            return Optional.of(((MemberPrincipal) principal).getMember());
        }
        return Optional.empty();
    }

    public Optional<ReviewGroup> getReviewGroup(Principal principal) {
        if (principal instanceof ReviewGroupPrincipal) {
            return Optional.of(((ReviewGroupPrincipal) principal).getReviewGroup());
        }
        return Optional.empty();
    }
}
