package shop.bookbom.auth.token.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import shop.bookbom.auth.token.RefreshToken;

@Repository
public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, String> {
}
