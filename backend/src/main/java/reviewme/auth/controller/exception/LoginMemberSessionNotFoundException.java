package reviewme.auth.controller.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class LoginMemberSessionNotFoundException extends BadRequestException {

    public LoginMemberSessionNotFoundException() {
        super("로그인 회원 세션이 존재하지 않아요.");
    }
}
