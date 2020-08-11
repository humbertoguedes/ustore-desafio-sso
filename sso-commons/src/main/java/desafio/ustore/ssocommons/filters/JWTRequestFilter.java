package desafio.ustore.ssocommons.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.GenericFilterBean;


@Configuration
@PropertySource(value = { "classpath:application.yml" })
public class JWTRequestFilter extends GenericFilterBean {

	@Value( "${sso.check.url}" )
	private String checkToken;
	
	@Value( "${sso.check.auth}" )
	private String authToken;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map<String, ?> checkToken(String token) {
		try {
			MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
			param.add("token", token);
			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(param, new HttpHeaders());
			ResponseEntity<Map> responseEntity = new RestTemplate().postForEntity(checkToken, request, Map.class);
			return responseEntity.getBody();
		} catch (HttpStatusCodeException e) {
//			ResponseEntity responseEntity = ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
//					.body(e.getResponseBodyAsString());
		}

		return null;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		if (Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {

			Function<HttpServletRequest, String> requestAuth = (requestParam) -> {
				return Optional.ofNullable(requestParam.getHeader("Authorization"))
						.filter(fa -> fa.startsWith("Bearer")).map((a) -> {
							return a.substring(7);
						}).orElse(requestParam.getParameter("token"));
			};

			String token = requestAuth.apply(((HttpServletRequest) request));

			if (Objects.nonNull(token)) {
				authenticate(token, request);
			} else {
				String location = String.format("%s?redirect-to=%s", authToken,
						((HttpServletRequest) request).getRequestURL().toString());
				((HttpServletResponse) response).sendRedirect(location);
			}
		}

		chain.doFilter(request, response);
	}

	private boolean authenticate(String token, ServletRequest request) {
		Map<String, ?> check = checkToken(token);

		if (Objects.nonNull(check) && (Boolean) check.get("active")) {
			String username = (String) check.get("sub");

			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
					null, new ArrayList<>());
			authenticationToken.setDetails(new WebAuthenticationDetails((HttpServletRequest) request));
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}

		return false;
	}

}
