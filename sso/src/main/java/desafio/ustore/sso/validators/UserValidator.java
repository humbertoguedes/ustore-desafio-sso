package desafio.ustore.sso.validators;

import java.util.Objects;

import org.springframework.stereotype.Component;

import desafio.ustore.sso.models.User;

@Component
public class UserValidator {

	public void userFull(User user) {
		Objects.requireNonNull(user.getName(), "Nome não pode ser vazio");
		Objects.requireNonNull(user.getUsername(), "Login não pode ser vazio");
		Objects.requireNonNull(user.getPassword(), "Senha não pode ser vazio");
	}

}
