package desafio.ustore.sso.configs;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import desafio.ustore.sso.models.Token;
import desafio.ustore.sso.services.TokenService;

@Component
public class RefererAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler
		implements AuthenticationSuccessHandler {

	@Autowired
	TokenService tokenService;

	public RefererAuthenticationSuccessHandler() {
		super();
		setAlwaysUseDefaultTargetUrl(true);
		setDefaultTargetUrl("/admin");
	}
	
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		if(Objects.nonNull(request.getCookies())) {
			Token token = tokenService.createToken((UserDetails) authentication.getPrincipal());
			final String parameter = "redirect-to";

			String redirectTo = Arrays.asList(request.getCookies()).stream().filter(c -> {
				return c.getName().equals(parameter);
			}).findFirst().map(Cookie::getValue).orElse("#").concat("?token="+token.getToken());
			
			setDefaultTargetUrl(redirectTo);
		}
		
		super.onAuthenticationSuccess(request, response, authentication);
	}
	
	

}