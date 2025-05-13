package auth.test.domain.test.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/check")
public class TestController {

    @GetMapping("/auth")
    public ResponseEntity<String> authTest() {
        return new ResponseEntity<>("인증된 사용자입니다.", HttpStatus.OK);
    }
}
