package shop.bookbom.auth.common.exception;

public class SecureManagerException extends BaseException {
    public SecureManagerException() {
        super(ErrorCode.COMMON_SYSTEM_ERROR, "Secure Key Manager 설정 중 오류가 발생했습니다.");
    }
}
