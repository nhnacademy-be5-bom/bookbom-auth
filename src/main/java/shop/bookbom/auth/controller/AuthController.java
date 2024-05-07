package shop.bookbom.auth.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.bookbom.auth.common.CommonResponse;
import shop.bookbom.auth.common.exception.BaseException;
import shop.bookbom.auth.common.exception.ErrorCode;
import shop.bookbom.auth.member.SignInDTO;
import shop.bookbom.auth.service.JwtReturnService;
import shop.bookbom.auth.token.dto.AccessNRefreshTokenDto;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtReturnService jwtReturnService;

    /**
     * 회원가입 또는 refresh token이 만료되었을 때
     * accessToken과 refreshToken을 모두 cookie로 발급받는 api
     * cookie는 모두 httpOnly이다.
     *
     * @param signInDTO
     * @return accessNRefreshTokenDto : accessToken refreshToken
     */
    @PostMapping("/token")
    public CommonResponse<AccessNRefreshTokenDto> getAccessNRefreshToken(HttpServletResponse response,
                                                                         @RequestBody @Valid SignInDTO signInDTO,
                                                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BaseException(ErrorCode.COMMON_INVALID_PARAMETER);
        }
        AccessNRefreshTokenDto accessNRefreshTokenDto = jwtReturnService.createAccessNRefreshToken(signInDTO);

        Cookie accessTokenCookie = new Cookie("accessToken", accessNRefreshTokenDto.getAccessToken());
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setSecure(true);
        response.addCookie(accessTokenCookie);

        Cookie refreshTokenCookie = new Cookie("refreshToken", accessNRefreshTokenDto.getRefreshToken());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setSecure(true);
        response.addCookie(refreshTokenCookie);
        log.info("getAccessNRefreshToken : \n accessToken : " + accessNRefreshTokenDto.getAccessToken() +
                "\n refreshToken : " + accessNRefreshTokenDto.getRefreshToken());
        return CommonResponse.successWithData(accessNRefreshTokenDto);
    }

    /**
     * refreshToken을 가지고 accessToken을 cookie로 발급받는 api
     *
     * @param refreshToken
     * @return
     */
    @PostMapping("/token/refresh")
    public CommonResponse refreshAccessToken(HttpServletResponse response,
                                             @RequestBody @Valid @NotBlank String refreshToken,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BaseException(ErrorCode.COMMON_INVALID_PARAMETER);
        }
        log.info(refreshToken);

        String accessToken = jwtReturnService.refreshAccessToken(refreshToken);

        Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setSecure(true);
        response.addCookie(accessTokenCookie);

        return CommonResponse.success();
    }

}
