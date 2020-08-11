package desafio.ustore.sso.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import desafio.ustore.sso.models.Token;

public interface TokenRepository extends JpaRepository<Token, Integer> {

	Optional<Token> findByCode(String code);
	
	Optional<Token> findByUsername(String username);

}
