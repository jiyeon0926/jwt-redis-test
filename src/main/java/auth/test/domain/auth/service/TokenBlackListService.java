package auth.test.domain.auth.service;

import auth.test.domain.auth.entity.TokenBlackList;
import auth.test.domain.auth.repository.TokenBlackListRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenBlackListService {

    @Getter
    @Value("${jwt.expiry-millis}")
    private long expiryMillis;

    private final TokenBlackListRepository tokenBlackListRepository;

    public void saveAccessToken(String accessToken) {
        boolean present = tokenBlackListRepository.findByAccessToken(accessToken).isPresent();

        if (present) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 등록된 Access Token 입니다.");
        }

        long now = new Date().getTime();
        long remainExpiry = now - expiryMillis;

        TokenBlackList blackList = TokenBlackList.builder()
                .accessToken(accessToken)
                .ttl(remainExpiry)
                .build();

        tokenBlackListRepository.save(blackList);
    }
}
