package reviewme.reviewgroup.service.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GroupAccessCodeNullException extends NullPointerException{
    public GroupAccessCodeNullException() {
        super("비회원 리뷰 그룹 생성은 그룹 액세스 코드를 필수로 입력해야 해요.");
        log.info("Non-member review group creation failed: Group access code is required");
    }
}
