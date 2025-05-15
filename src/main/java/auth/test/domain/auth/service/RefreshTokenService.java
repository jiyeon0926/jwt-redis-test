package auth.test.domain.auth.service;

import auth.test.domain.auth.entity.RefreshToken;
import auth.test.domain.auth.repository.RefreshTokenRepository;
import auth.test.global.auth.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;

    public void saveRefreshToken(String refreshToken, String email) {
        long refreshExpiryMillis = jwtProvider.getExpirationDateFromToken(refreshToken).getTime();

        RefreshToken refresh = RefreshToken.builder()
                .refreshToken(refreshToken)
                .authKey(email)
                .ttl(refreshExpiryMillis)
                .build();

        refreshTokenRepository.save(refresh);
    }

    public void deleteRefreshToken(String email) {
        refreshTokenRepository.findByAuthKey(email)
                .ifPresent(token -> refreshTokenRepository.delete(token));
    }

    public void validByRefreshToken(String refreshToken) {
        refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유효하지 않은 Refresh Token 입니다."));
    }
}
