package shop.bookbom.auth.config.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeystoreResponse {
    private Header header;
    private Body body;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Header {
        private Integer resultCode;
        private String resultMessage;
        private boolean isSuccessful;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Body {
        private String secret;
    }
}

