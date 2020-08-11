package desafio.ustore.appui.apis;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Carrinho implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username;
	
	private BigDecimal valorTotal;

	public Carrinho(String username, BigDecimal valorTotal) {
		this.username = username;
		this.valorTotal = valorTotal;
	}
	
	
}