package auth.test.domain.auth.service;

import auth.test.domain.auth.entity.TokenBlackList;
import auth.test.domain.auth.repository.TokenBlackListRepository;
import auth.test.global.auth.jwt.JwtProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenBlackListService {

    private final TokenBlackListRepository tokenBlackListRepository;
    private final JwtProvider jwtProvider;

    public void saveAccessToken(String accessToken) {
        long now = new Date().getTime();
        Claims claims = jwtProvider.getClaims(accessToken);
        Date expiration = claims.getExpiration();
        long remainExpiration = expiration.getTime() - now;

        if (remainExpiration > 0) {
            TokenBlackList blackList = TokenBlackList.builder()
                    .accessToken(accessToken)
                    .ttl(remainExpiration)
                    .build();

            tokenBlackListRepository.save(blackList);
        }
    }

    public boolean validBlackList(String accessToken) {
        return tokenBlackListRepository.findByAccessToken(accessToken).isPresent();
    }
}
