package auth.test.domain.user.controller;

import auth.test.domain.user.dto.SignupReqDto;
import auth.test.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupReqDto signupReqDto) {
        userService.signup(signupReqDto.getEmail());

        return new ResponseEntity<>("회원가입 완료", HttpStatus.CREATED);
    }
}
