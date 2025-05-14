package auth.test.domain.user.dto;

import lombok.Getter;

@Getter
public class LoginReqDto {

    private String email;
    private String password;
}
