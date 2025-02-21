package reviewme.member.service.dto;

public record ProfileResponse(
        long memberId,
        String nickname,
        String profileImageUrl
) {
}
