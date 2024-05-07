package shop.bookbom.auth.common;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CommonListResponse<T> {
    private static final String SUCCESS_MESSAGE = "SUCCESS";
    private ResponseHeader header;
    private List<T> result;
    private int totalCount;

    @Builder
    private CommonListResponse(ResponseHeader header, List<T> result, int totalCount) {
        this.header = header;
        this.result = result;
        this.totalCount = totalCount;
    }

    public static <T> CommonListResponse<T> successWithList(List<T> data) {
        return CommonListResponse.<T>builder()
                .header(ResponseHeader.builder()
                        .isSuccessful(true)
                        .resultCode(HttpStatus.OK.value())
                        .resultMessage(SUCCESS_MESSAGE).build())
                .result(data)
                .totalCount(data.size())
                .build();
    }
}
