package desafio.ustore.appui.controllers;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import desafio.ustore.appui.apis.Carrinho;
import desafio.ustore.appui.apis.ShopApi;

@Controller
public class UIController {

	@Autowired
	private ShopApi shopApi;

	@RequestMapping(value = "/")
	public String loadUI() {
		return "inicio";
	}

	@RequestMapping(value = "/dashboard")
	public String loadSecuredUI() {
		return "dashboard";
	}
	
	@RequestMapping(value = "/meu-carrinho")
	public String meuCarrinho(Model model) {
		Carrinho carrinho = shopApi.carrinho();
		model.addAttribute("carrinhos", Arrays.asList(carrinho));
		return "dashboard";
	}

	@RequestMapping(value = "/carrinhos")
	public String loadCarrinhos(Model model) {
		model.addAttribute("carrinhos", shopApi.carrinhos());
		return "dashboard";
	}
	
	@RequestMapping(value = "/logout")
	public String logout() {
		((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession().invalidate();
		return "home";
	}
	
	
	

}
