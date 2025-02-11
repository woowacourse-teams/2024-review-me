package reviewme.review.service.dto.response.list;

public record ReviewCountResponse(
        long reviewGroupId,
        int totalReviewCount
) {
}
