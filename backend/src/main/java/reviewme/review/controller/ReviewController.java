package reviewme.review.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reviewme.review.dto.request.CreateReviewRequest;
import reviewme.review.dto.response.ReviewResponse;
import reviewme.review.service.ReviewService;

@Tag(name = "리뷰 관리")
@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "리뷰 등록", description = "리뷰 작성 정보를 받아 리뷰를 등록한다.")
    @ApiResponse(responseCode = "201")
    @PostMapping("/reviews")
    public ResponseEntity<Void> createReview(@RequestBody CreateReviewRequest request) {
        long id = reviewService.createReview(request);
        return ResponseEntity.created(URI.create("/reviews/" + id)).build();
    }

    @Operation(summary = "리뷰 조회", description = "단일 리뷰를 조회한다.")
    @GetMapping("/reviews/{id}")
    public ResponseEntity<ReviewResponse> findReview(@PathVariable long id) {
        ReviewResponse response = reviewService.findReview(id);
        return ResponseEntity.ok(response);
    }
}
