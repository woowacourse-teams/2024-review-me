package reviewme.e2e;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import reviewme.reviewgroup.dto.ReviewGroupCreationRequest;
import reviewme.reviewgroup.dto.ReviewGroupCreationResponse;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.support.E2ETest;

class ReviewsE2ETest extends E2ETest {

    @Autowired
    private ReviewGroupRepository reviewGroupRepository;

    @Test
    void 리뷰방_생성_API() {
        // given
        ReviewGroupCreationRequest request = new ReviewGroupCreationRequest("revieweeName", "projectName");

        // when
        ReviewGroupCreationResponse response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/v2/groups")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(ReviewGroupCreationResponse.class);

        // then
        assertAll(
                () -> assertThat(response.reviewRequestCode()).isNotBlank(),
                () -> assertThat(response.groupAccessCode()).isNotBlank()
        );
    }
}
