package desafio.ustore.appshop.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import desafio.ustore.appshop.models.Carrinho;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Integer> {

	Optional<Carrinho> findByUsername(String username);

}