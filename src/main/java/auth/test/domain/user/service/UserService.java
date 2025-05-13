package auth.test.domain.user.service;

import auth.test.domain.user.entity.User;
import auth.test.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void signup(String email) {
        User user = new User(email);

        userRepository.save(user);
    }
}
