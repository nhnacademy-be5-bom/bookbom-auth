package shop.bookbom.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import shop.bookbom.auth.common.exception.BaseException;
import shop.bookbom.auth.common.exception.ErrorCode;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RefreshTokenNotFoundException extends BaseException {
    public RefreshTokenNotFoundException() {
        super(ErrorCode.REFRESHTOKEN_NOT_FOUND);
    }

    public RefreshTokenNotFoundException(ErrorCode errorCode, String message) {
        super(ErrorCode.REFRESHTOKEN_NOT_FOUND, "존재하지 않는 사용자 입니다.");
    }
}
