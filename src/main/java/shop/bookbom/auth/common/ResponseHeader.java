package shop.bookbom.auth.common;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseHeader {
    private boolean isSuccessful;
    private int resultCode;
    private String resultMessage;

    @Builder
    public ResponseHeader(boolean isSuccessful, int resultCode, String resultMessage) {
        this.isSuccessful = isSuccessful;
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }
}
