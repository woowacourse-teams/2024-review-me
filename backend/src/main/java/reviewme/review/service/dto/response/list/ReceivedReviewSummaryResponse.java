package reviewme.review.service.dto.response.list;

public record ReceivedReviewSummaryResponse(
        String projectName,
        String revieweeName,
        int totalReviewCount
) {
}
