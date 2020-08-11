package desafio.ustore.appshop.models;

import java.math.BigDecimal;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "produto_item")
@Data
@NoArgsConstructor
public class ProdutoItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	private Produto produto;

	@ManyToOne
	private Carrinho carrinho;

	@Column(name = "nome")
	private int quantidade;
	
	public BigDecimal getValorTotal() {
		return Optional.ofNullable(produto).isPresent()? produto.getPreco().multiply(BigDecimal.valueOf(quantidade)):BigDecimal.ZERO;
	}

}
