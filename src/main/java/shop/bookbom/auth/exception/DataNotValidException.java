package shop.bookbom.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import shop.bookbom.auth.common.exception.BaseException;
import shop.bookbom.auth.common.exception.ErrorCode;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DataNotValidException extends BaseException {

    public DataNotValidException() {
        super(ErrorCode.DATA_NOT_VALID);
    }

    public DataNotValidException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
