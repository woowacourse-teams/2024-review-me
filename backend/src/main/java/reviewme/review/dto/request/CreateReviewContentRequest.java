package reviewme.review.dto.request;

public record CreateReviewContentRequest(
        Long order,
        String question,
        String answer) {
}
