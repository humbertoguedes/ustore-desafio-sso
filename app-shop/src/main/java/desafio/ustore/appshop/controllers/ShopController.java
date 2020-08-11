package desafio.ustore.appshop.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import desafio.ustore.appshop.models.Carrinho;
import desafio.ustore.appshop.repositories.CarrinhoRepository;

@RestController
@RequestMapping(value = "/shop")
public class ShopController {

	@Autowired
	CarrinhoRepository carrinhoRepository;

	@GetMapping(value = "/meucarrinho")
	@ResponseBody
	public Carrinho carrinho() {
		return new Carrinho();
	}

	@PreAuthorize("hasRole('ROLE_admin')")
	@GetMapping(value = "/todos")
	@ResponseBody
	public List<Carrinho> carrinhos() {
		return carrinhoRepository.findAll();
	}

}
