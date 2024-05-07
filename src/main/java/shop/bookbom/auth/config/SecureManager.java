package shop.bookbom.auth.config;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import shop.bookbom.auth.config.dto.KeystoreResponse;

@Component
@RequiredArgsConstructor
public class SecureManager {
    private final RestTemplate restTemplateWithTLS;

    @Value("${keymanager.url}")
    private String keyManagerUrl;

    @Value("${secure.manager.appkey}")
    private String appkey;


    /**
     * 인증서를 통해 Secure key manager와 HTTPS 통신을 해서 값을 가져오는 메서드입니다.
     *
     * @param key Secure Key Manager에 저장한 값의 아이디
     * @return value 저장한 값
     */
    public String getValue(String key) {
        String url = keyManagerUrl + appkey + "/secrets/" + key;
        KeystoreResponse response = restTemplateWithTLS.getForObject(url, KeystoreResponse.class);
        return Objects.requireNonNull(response).getBody().getSecret();
    }
}
