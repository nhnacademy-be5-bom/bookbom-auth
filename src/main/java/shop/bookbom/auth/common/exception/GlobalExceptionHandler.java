package shop.bookbom.auth.common.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.bookbom.auth.common.CommonResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public CommonResponse<Void> handleBaseException(BaseException e) {
        return CommonResponse.fail(e.getErrorCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResponse<Void> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        // 사용자 정의 예외를 던질 수 있습니다.
        return CommonResponse.fail(ErrorCode.COMMON_INVALID_PARAMETER);
    }
}
