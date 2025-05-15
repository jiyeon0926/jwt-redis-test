package auth.test.domain.auth.repository;

import auth.test.domain.auth.entity.TokenBlackList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenBlackListRepository extends CrudRepository<TokenBlackList, Long> {

    Optional<TokenBlackList> findByAccessToken(String accessToken);
}
