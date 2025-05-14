package auth.test.domain.auth.dto;

import lombok.Getter;

@Getter
public class TokenDto {

    private String accessToken;
    private String refreshToken;
}
