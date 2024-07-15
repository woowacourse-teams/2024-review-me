package reviewme.member.exception;

import reviewme.global.exception.NotFoundException;

public class MemberNotFoundException extends NotFoundException {

    public MemberNotFoundException() {
        super("회원이 존재하지 않습니다.");
    }
}
