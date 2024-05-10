package shop.bookbom.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import shop.bookbom.auth.common.exception.BaseException;
import shop.bookbom.auth.common.exception.ErrorCode;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TokenIsNullException extends BaseException {
    public TokenIsNullException() {
        super(ErrorCode.REFRESHTOKEN_IS_NULL);
    }

    public TokenIsNullException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
