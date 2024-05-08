package shop.bookbom.auth.adapter.impl;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import shop.bookbom.auth.adapter.UserRoleAdapter;
import shop.bookbom.auth.common.CommonResponse;
import shop.bookbom.auth.member.SignInDTO;
import shop.bookbom.auth.member.UserDto;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserRoleAdapterImpl implements UserRoleAdapter {
    private static final ParameterizedTypeReference<CommonResponse<UserDto>> USER_RESPONSE =
            new ParameterizedTypeReference<>() {
            };

    private final RestTemplate restTemplate;

    @Value("${bookbom.gateway-url}")
    String gatewayUrl;


    @Override
    public CommonResponse<UserDto> signIn(SignInDTO signInDTO) {
        log.info("auth server signin dto : " + signInDTO.getEmail() + " " + signInDTO.getPassword());
        URI uri = UriComponentsBuilder
                .fromUriString(gatewayUrl)
                .path("/shop/open/users/detail")
                .encode()
                .build()
                .toUri();

        RequestEntity<SignInDTO> requestEntity = RequestEntity.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .body(signInDTO);

        log.info("send request");
        // 토큰 요청 전송
        ResponseEntity<CommonResponse<UserDto>> responseEntity = restTemplate.exchange(
                requestEntity, USER_RESPONSE);

        log.info(requestEntity.getBody().getEmail());

        return responseEntity.getBody();
    }
}
