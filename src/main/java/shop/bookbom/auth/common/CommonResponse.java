package shop.bookbom.auth.common;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import shop.bookbom.auth.common.exception.ErrorCode;

@Data
@NoArgsConstructor
public class CommonResponse<T> {
    private static final String SUCCESS_MESSAGE = "SUCCESS";
    private ResponseHeader header;
    private T result;

    @Builder
    private CommonResponse(ResponseHeader header, T result) {
        this.header = header;
        this.result = result;
    }

    public static CommonResponse<Void> success() {
        return CommonResponse.<Void>builder()
                .header(ResponseHeader.builder()
                        .isSuccessful(true)
                        .resultCode(HttpStatus.OK.value())
                        .resultMessage(SUCCESS_MESSAGE).build())
                .build();
    }

    public static <T> CommonResponse<T> successWithData(T data) {
        return CommonResponse.<T>builder()
                .header(ResponseHeader.builder()
                        .isSuccessful(true)
                        .resultCode(HttpStatus.OK.value())
                        .resultMessage(SUCCESS_MESSAGE).build())
                .result(data)
                .build();
    }

    public static CommonResponse<Void> fail(ErrorCode errorCode) {
        return CommonResponse.<Void>builder()
                .header(ResponseHeader.builder()
                        .isSuccessful(false)
                        .resultCode(errorCode.getCode())
                        .resultMessage(errorCode.getMessage()).build())
                .build();
    }
}
