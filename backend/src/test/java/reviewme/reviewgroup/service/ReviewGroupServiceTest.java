package reviewme.reviewgroup.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.dto.ReviewGroupCreationRequest;
import reviewme.reviewgroup.dto.ReviewGroupCreationResponse;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.support.ServiceTest;

@ServiceTest
@ExtendWith(MockitoExtension.class)
class ReviewGroupServiceTest {

    @MockBean
    private RandomCodeGenerator randomCodeGenerator;

    @Autowired
    private ReviewGroupService reviewGroupService;

    @Autowired
    private ReviewGroupRepository reviewGroupRepository;

    @Test
    void 코드가_중복되는_경우_다시_생성한다() {
        // given
        reviewGroupRepository.save(new ReviewGroup("reviewee", "project", "0000", "1111"));
        given(randomCodeGenerator.generate(anyInt()))
                .willReturn("0000") // ReviewRequestCode
                .willReturn("AAAA")
                .willReturn("1111") // GroupAccessCode
                .willReturn("BBBB");
        ReviewGroupCreationRequest request = new ReviewGroupCreationRequest("sancho", "reviewme");

        // when
        ReviewGroupCreationResponse response = reviewGroupService.createReviewGroup(request);

        // then
        assertThat(response).isEqualTo(new ReviewGroupCreationResponse("AAAA", "BBBB"));
        then(randomCodeGenerator).should(times(4)).generate(anyInt());
    }
}
