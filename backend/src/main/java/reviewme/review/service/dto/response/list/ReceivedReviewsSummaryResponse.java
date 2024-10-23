package reviewme.review.service.dto.response.list;

public record ReceivedReviewsSummaryResponse(
        String projectName,
        String revieweeName,
        int totalReviewCount
) {
}
