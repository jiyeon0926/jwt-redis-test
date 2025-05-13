package auth.test.domain.user.service;

import auth.test.domain.user.dto.AuthResDto;
import auth.test.domain.user.entity.User;
import auth.test.domain.user.repository.UserRepository;
import auth.test.global.auth.util.AuthenticationScheme;
import auth.test.global.auth.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Transactional
    public void signup(String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);

        User user = new User(email, encodedPassword);
        userRepository.save(user);
    }

    public AuthResDto login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        validatePassword(password, user.getPassword());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtProvider.generateAccessToken(authentication);

        return new AuthResDto(AuthenticationScheme.BEARER.getName(), accessToken);
    }

    private void validatePassword(String rawPassword, String encodedPassword) {
        boolean notValid = !passwordEncoder.matches(rawPassword, encodedPassword);

        if (notValid) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 틀렸습니다.");
        }
    }
}
