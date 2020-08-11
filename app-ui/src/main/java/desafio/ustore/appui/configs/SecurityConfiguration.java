package desafio.ustore.appui.configs;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import desafio.ustore.ssocommons.filters.JWTRequestFilter;

@Component
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/**").permitAll()
//		.anyRequest().authenticated()
		;

		http.addFilterBefore(new JWTRequestFilter(), UsernamePasswordAuthenticationFilter.class);
	}

}
