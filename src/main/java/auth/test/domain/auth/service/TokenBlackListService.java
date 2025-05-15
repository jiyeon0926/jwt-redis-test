package auth.test.domain.auth.service;

import auth.test.domain.auth.entity.TokenBlackList;
import auth.test.domain.auth.repository.TokenBlackListRepository;
import auth.test.global.auth.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class TokenBlackListService {

    private final TokenBlackListRepository tokenBlackListRepository;
    private final JwtProvider jwtProvider;

    public void saveAccessToken(String accessToken) {
        boolean present = tokenBlackListRepository.findByAccessToken(accessToken).isPresent();

        if (present) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 등록된 Access Token 입니다.");
        }

        long now = System.currentTimeMillis();
        long expiryMillis = jwtProvider.getExpirationDateFromToken(accessToken).getTime();
        long remainExpiry = expiryMillis - now;

        TokenBlackList blackList = TokenBlackList.builder()
                .accessToken(accessToken)
                .ttl(remainExpiry)
                .build();

        tokenBlackListRepository.save(blackList);
    }
}
