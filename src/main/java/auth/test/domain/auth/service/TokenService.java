package auth.test.domain.auth.service;

import auth.test.domain.auth.entity.RefreshToken;
import auth.test.domain.auth.repository.TokenRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class TokenService {

    @Getter
    @Value("${jwt.refresh-expiry-millis}")
    private long refreshExpiryMillis;

    private final TokenRepository tokenRepository;

    public void saveRefreshToken(String refreshToken, String email) {
        RefreshToken refresh = RefreshToken.builder()
                .refreshToken(refreshToken)
                .authKey(email)
                .ttl(refreshExpiryMillis)
                .build();

        tokenRepository.save(refresh);
    }

    public void removeRefreshToken(String refreshToken) {
        tokenRepository.findById(refreshToken)
                .ifPresent(token -> tokenRepository.delete(token));
    }

    public void validRefreshToken(String refreshToken) {
        RefreshToken redisToken = tokenRepository.findById(refreshToken)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유효하지 않은 Refresh Token입니다."));
    }
}
