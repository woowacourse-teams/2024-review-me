package reviewme.reviewgroup.service.dto;

public record ReviewGroupCreationResponse(
        String reviewRequestCode,
        String groupAccessCode
) {
}
