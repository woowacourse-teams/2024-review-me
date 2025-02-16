package reviewme.fixture;

import java.util.List;
import reviewme.review.domain.Answer;
import reviewme.review.domain.Review;

public class ReviewFixture {

    public static Review 비회원_작성_리뷰(long templateId, long reviewGroupId, List<Answer> answers) {
        return new Review(null, templateId, reviewGroupId, answers);
    }

    public static Review 회원_작성_리뷰(Long memberId, long templateId, long reviewGroupId, List<Answer> answers) {
        return new Review(memberId, templateId, reviewGroupId, answers);
    }
}
