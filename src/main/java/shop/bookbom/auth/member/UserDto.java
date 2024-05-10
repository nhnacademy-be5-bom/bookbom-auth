package shop.bookbom.auth.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDto {

    private Long userId;
    private String role;

    @Builder
    public UserDto(Long userId, String role) {
        this.userId = userId;
        this.role = role;
    }
}
