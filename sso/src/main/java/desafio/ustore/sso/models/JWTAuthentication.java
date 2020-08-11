package desafio.ustore.sso.models;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class JWTAuthentication {

	private final String type = "jwt";
	private String token;

	public JWTAuthentication(String token) {
		this.token = token;
	}

}
