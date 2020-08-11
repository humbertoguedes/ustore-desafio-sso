package desafio.ustore.sso.filters;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import desafio.ustore.sso.services.UserDetailsCustomService;
import desafio.ustore.sso.utils.JWTUtil;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

	@Autowired
	UserDetailsCustomService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		Optional.ofNullable(request.getParameter("redirect-to"))
				.ifPresent(p -> response.addCookie(new Cookie("redirect-to", p)));

		final String authorization = request.getHeader("Authorization");
		String jwt = null, username = null;

		if (Objects.nonNull(authorization) && authorization.startsWith("Bearer")) {
			final String[] splitAuth = authorization.split("\s");
			jwt = splitAuth[1];
			username = JWTUtil.extractUsername(jwt);
		}

		if (Objects.nonNull(username) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
			final UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			if (JWTUtil.validateToken(jwt, userDetails)) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails.getUsername(), null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}

		filterChain.doFilter(request, response);
	}

}
