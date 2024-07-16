package reviewme.review;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    public ResponseEntity<Void> createReview(@RequestBody CreateReviewRequest request) {
        Long review = reviewService.createReview(request);
        return ResponseEntity.created(URI.create("/reviews/" + review)).build();
    }

    @GetMapping("/reviews/{id}")
    public ResponseEntity<ReviewResponse> findReview(@PathVariable long id) {
        ReviewResponse response = reviewService.findReview(id);
        return ResponseEntity.ok(response);
    }
}
