package auth.test.domain.auth.repository;

import auth.test.domain.auth.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends CrudRepository<RefreshToken, String> {
}