package shop.bookbom.auth.controller;

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
import shop.bookbom.auth.exception.DataNotValidException;
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
    public CommonResponse<AccessNRefreshTokenDto> getAccessNRefreshToken(@RequestBody @Valid SignInDTO signInDTO,
                                                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new DataNotValidException();
        }
        AccessNRefreshTokenDto accessNRefreshTokenDto = jwtReturnService.createAccessNRefreshToken(signInDTO);
        return CommonResponse.successWithData(accessNRefreshTokenDto);
    }

    /**
     * refreshToken을 가지고 accessToken을 cookie로 발급받는 api
     *
     * @param refreshToken
     * @return
     */
    @PostMapping("/token/refresh")
    public CommonResponse<String> refreshAccessToken(HttpServletResponse response,
                                                     @RequestBody @Valid @NotBlank String refreshToken,
                                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new DataNotValidException();
        }
        String accessToken = jwtReturnService.refreshAccessToken(refreshToken);
        return CommonResponse.successWithData(accessToken);
    }

}
