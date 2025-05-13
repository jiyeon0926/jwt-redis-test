package auth.test.domain.user.controller;

import auth.test.domain.user.dto.AuthReqDto;
import auth.test.domain.user.dto.AuthResDto;
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
        userService.signup(signupReqDto.getEmail(), signupReqDto.getPassword());

        return new ResponseEntity<>("회원가입 완료", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResDto> login(@RequestBody AuthReqDto authReqDto) {
        AuthResDto login = userService.login(authReqDto.getEmail(), authReqDto.getPassword());

        return new ResponseEntity<>(login, HttpStatus.OK);
    }
}