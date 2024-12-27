package reviewme.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reviewme.member.service.MemberService;
import reviewme.member.service.dto.ProfileResponse;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/v2/members/profile")
    public ResponseEntity<ProfileResponse> getProfile() {
        ProfileResponse response = memberService.getProfile();
        return ResponseEntity.ok(response);
    }
}
