package auth.test.domain.auth.service;

import auth.test.domain.auth.dto.LoginResDto;
import auth.test.domain.auth.dto.TokenDto;
import auth.test.domain.user.entity.User;
import auth.test.domain.user.repository.UserRepository;
import auth.test.global.auth.enums.AuthenticationScheme;
import auth.test.global.auth.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final TokenBlackListService tokenBlackListService;

    public LoginResDto login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        validPassword(password, user.getPassword());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtProvider.generateAccessToken(authentication);
        String refreshToken = jwtProvider.generateRefreshToken(authentication);

        return new LoginResDto(AuthenticationScheme.BEARER.getName(), accessToken, refreshToken);
    }

    public void logout(String accessToken) {
        tokenBlackListService.saveAccessToken(accessToken);

        String email = jwtProvider.getUsername(accessToken);
        refreshTokenService.deleteRefreshToken(email);
    }

    public TokenDto refresh(String refreshToken) {
        refreshTokenService.validRefreshToken(refreshToken);

        String email = jwtProvider.getUsername(refreshToken);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, null, List.of());

        String accessToken = jwtProvider.generateAccessToken(authenticationToken);

        refreshTokenService.deleteRefreshToken(email);
        String newRefreshToken = jwtProvider.generateRefreshToken(authenticationToken);
        refreshTokenService.saveRefreshToken(newRefreshToken, email);

        return new TokenDto(accessToken, newRefreshToken);
    }

    private void validPassword(String rawPassword, String encodedPassword) {
        boolean notValid = !passwordEncoder.matches(rawPassword, encodedPassword);

        if (notValid) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 틀렸습니다.");
        }
    }
}
