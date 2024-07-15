package reviewme.review;

public record CreateReviewContentRequest(
        Long order,
        String question,
        String answer) {
}
