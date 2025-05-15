package auth.test.domain.auth.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.util.concurrent.TimeUnit;

@RedisHash("tokenBlackList")
@Getter
@NoArgsConstructor
public class TokenBlackList {

    @Id
    @Indexed
    private String accessToken;

    private String authKey;

    @TimeToLive(unit = TimeUnit.MILLISECONDS)
    private Long ttl;

    @Builder
    public TokenBlackList(String accessToken, String authKey, Long ttl) {
        this.accessToken = accessToken;
        this.authKey = authKey;
        this.ttl = ttl;
    }
}
