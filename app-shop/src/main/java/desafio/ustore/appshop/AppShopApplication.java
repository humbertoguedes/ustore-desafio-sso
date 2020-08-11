package desafio.ustore.appshop;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import desafio.ustore.appshop.models.Carrinho;
import desafio.ustore.appshop.repositories.CarrinhoRepository;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppShopApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner loadData(CarrinhoRepository repository) {
	    return (args) -> {

	    	Carrinho otherCarrinho = new Carrinho();
	    	otherCarrinho.setUsername("usuarioOther");
	    	repository.save(otherCarrinho);
	    	
	    	Carrinho meuCarrinho = new Carrinho();
	    	meuCarrinho.setUsername("usuario1");
			repository.save(meuCarrinho);
			
	    };
	}

}
