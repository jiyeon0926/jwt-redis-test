package auth.test.domain.auth.service;

import auth.test.domain.auth.entity.RefreshToken;
import auth.test.domain.auth.repository.TokenRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
}
