package shop.bookbom.auth.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Arrays;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import shop.bookbom.auth.adapter.UserRoleAdapter;
import shop.bookbom.auth.common.CommonResponse;
import shop.bookbom.auth.common.exception.BaseException;
import shop.bookbom.auth.common.exception.ErrorCode;
import shop.bookbom.auth.exception.RefreshTokenNotFoundException;
import shop.bookbom.auth.member.SignInDTO;
import shop.bookbom.auth.member.UserDto;
import shop.bookbom.auth.token.RefreshToken;
import shop.bookbom.auth.token.dto.AccessNRefreshTokenDto;
import shop.bookbom.auth.token.repository.RefreshTokenRedisRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtReturnService {

    @Value("${jwt.secretKey}")
    private String secretKey;

    private final RefreshTokenRedisRepository redisRepository;

    private final UserRoleAdapter userRoleAdapter;

    /**
     * ACCESS TOKEN & REFRESH TOKEN 발급
     * REFRESH TOKEN이 만료되었거나, 로그인 할 때 사용한다.
     * REFRESH TOKEN은 UUID.randomUUID().toString로 발급한다.
     * 발급 후 REDIS에 저장하여 관리한다.
     */
    public AccessNRefreshTokenDto createAccessNRefreshToken(SignInDTO signInDTO) {
        // shop 서버에서 userId와 role을 받아온다
        CommonResponse<UserDto> userDtoResponse = userRoleAdapter.signIn(signInDTO);

        // 서버에서 정보를 받아오지 못하거나, 받아온 정보가 비어있을 때 USER_NOT_FOUND EXCEPTION을 던진다.
        if (!userDtoResponse.getHeader().isSuccessful() || userDtoResponse.getResult().getRole().isEmpty()) {
            throw new BaseException(ErrorCode.USER_NOT_FOUND, userDtoResponse.getHeader().getResultMessage());
        }

        UserDto userDto = userDtoResponse.getResult();

        // redis에 refresh token 저장. refresh token을 통해 accesstoken을 발급해야하므로 role도 함께 저장한다.
        // role을 함께 저장하는 이유는 서비스 특성상 role값의 변동이 거의 없고 shop 서버의 부하를 줄일 수 있기 때문.
        RefreshToken refreshToken = new RefreshToken(userDto.getUserId() + "|" + userDto.getRole());
        redisRepository.save(refreshToken);

        return AccessNRefreshTokenDto.builder()
                .accessToken(createJwt(userDto))
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }

    /**
     * refreshToken을 조회하여 accessToken을 발급
     */
    public String refreshAccessToken(String token) {
        // redis repository를 통해 refresh token을 찾는다
        // 만약 없으면 exception을 날린다
        RefreshToken refreshtoken = redisRepository.findById(token)
                .orElseThrow(RefreshTokenNotFoundException::new);

        // 있다면 refreshtoken과 함께 redis에 저장되어있던 user id와 role를 통해 accessToken을 만들어 전송한다.
        Long userId = Long.valueOf(refreshtoken.getUserIdNRole().split("\\|")[0]);
        String role = refreshtoken.getUserIdNRole().split("\\|")[1];
        log.info("role is : " + role);
        return createJwt(UserDto.builder().userId(userId).role(role).build());
    }

    /**
     * accessToken 발급을 위한 메소드
     */
    public String createJwt(UserDto userDto) {
        log.info(Arrays.toString(secretKey.getBytes()));

        String jwtToken = Jwts.builder()
                .claim("userId", userDto.getUserId())
                .claim("role", userDto.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000)) // 1시간으로 설정
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();

        log.info(jwtToken);
        return jwtToken;
    }

}
