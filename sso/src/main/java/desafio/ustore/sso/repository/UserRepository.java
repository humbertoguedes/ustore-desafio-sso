package desafio.ustore.sso.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import desafio.ustore.sso.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByUsername(String name);

}
