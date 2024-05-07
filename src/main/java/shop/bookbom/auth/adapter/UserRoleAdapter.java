package shop.bookbom.auth.adapter;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import shop.bookbom.auth.common.CommonResponse;
import shop.bookbom.auth.member.SignInDTO;
import shop.bookbom.auth.member.UserDto;

public interface UserRoleAdapter {

    CommonResponse<UserDto> signIn(SignInDTO signInDTO);
}
