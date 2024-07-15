package reviewme.review;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    public ResponseEntity<Void> createReview(@RequestBody CreateReviewRequest request) {
        Long review = reviewService.createReview(request);
        return ResponseEntity
                .created(URI.create("/reviews/" + review))
                .build();
    }
}
