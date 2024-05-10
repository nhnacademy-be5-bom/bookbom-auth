package shop.bookbom.auth.token;

import java.util.UUID;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(timeToLive = 3600 * 24) // 24시간동안 살아있도록 설정
public class RefreshToken {
    @Id
    private String refreshToken;
    private String userIdNRole;

    public RefreshToken(String userIdNRole) {
        this.refreshToken = UUID.randomUUID().toString();
        this.userIdNRole = userIdNRole;
    }
}
