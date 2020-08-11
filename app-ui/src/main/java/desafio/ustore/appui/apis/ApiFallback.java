package desafio.ustore.appui.apis;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ApiFallback implements ShopApi {

	@Override
	public Carrinho carrinho() {
		return new Carrinho("Produto FailBack", BigDecimal.ZERO);
	}

	@Override
	public List<Carrinho> carrinhos() {
		return Arrays.asList(carrinho(), carrinho());
	}

}
