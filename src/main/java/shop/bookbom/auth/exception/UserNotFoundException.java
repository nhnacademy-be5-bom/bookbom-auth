package shop.bookbom.auth.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import shop.bookbom.auth.common.exception.BaseException;
import shop.bookbom.auth.common.exception.ErrorCode;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends BaseException {
    public UserNotFoundException(ErrorCode errorCode, String message) {
        super(ErrorCode.USER_NOT_FOUND, "존재하지 않는 사용자 입니다.");
    }

    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
