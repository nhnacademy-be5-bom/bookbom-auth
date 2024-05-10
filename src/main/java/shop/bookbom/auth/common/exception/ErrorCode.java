package shop.bookbom.auth.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // common
    COMMON_SYSTEM_ERROR(500, "시스템 오류가 발생했습니다. 잠시 후 다시 시도해주세요."),
    COMMON_INVALID_PARAMETER(400, "요청한 값이 올바르지 않습니다."),
    COMMON_ENTITY_NOT_FOUND(400, "존재하지 않는 엔티티입니다."),
    COMMON_ILLEGAL_STATUS(400, "잘못된 상태값입니다."),
    // user
    USER_NOT_FOUND(400, "존재하지 않는 사용자입니다"),
    // token
    REFRESHTOKEN_IS_NULL(400, "요청한 값이 올바르지 않습니다"),
    DATA_NOT_VALID(400, "요청한 값이 올바르지 않습니다."),
    REFRESHTOKEN_NOT_FOUND(400, "리프래시 토큰이 존재하지 않습니다");
    private final int code;
    private final String message;
}
