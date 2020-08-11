package desafio.ustore.appui.apis;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "shop", url = "http://localhost:8010/", fallback = ApiFallback.class)
public interface ShopApi {

	@GetMapping(value = "/meucarrinho")
	public Carrinho carrinho();

	@GetMapping(value = "/")
	public List<Carrinho> carrinhos();
}




