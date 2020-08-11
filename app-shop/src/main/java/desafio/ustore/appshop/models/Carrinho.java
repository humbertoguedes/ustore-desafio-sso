package desafio.ustore.appshop.models;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "carrinho")
@Data
@NoArgsConstructor
public class Carrinho {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToMany(mappedBy = "carrinho", fetch = FetchType.EAGER)
	private List<ProdutoItem> itens;

	@Column(name = "username")
	private String username;

	public BigDecimal getValorTotal() {
		return Optional.ofNullable(itens).isPresent() ? itens.stream().map(ProdutoItem::getValorTotal).reduce(BigDecimal.ZERO, BigDecimal::add): BigDecimal.ZERO;
	}
}
