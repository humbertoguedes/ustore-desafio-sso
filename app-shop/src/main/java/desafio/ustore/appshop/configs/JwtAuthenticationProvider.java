//package desafio.ustore.appshop.configs;
//
//import java.util.ArrayList;
//import java.util.Map;
//
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.HttpStatusCodeException;
//import org.springframework.web.client.RestTemplate;
//
//public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
//
//	@Override
//	public boolean supports(Class<?> authentication) {
//		return (PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication));
//	}
//
//	@Override
//	protected void additionalAuthenticationChecks(UserDetails userDetails,
//			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
//	}
//
//	@Override
//	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
//			throws AuthenticationException {
//
//		User parsedUser = null;
//
//		String token = authentication.getName();
//		try {
//			Map check = checkToken(token);
//			parsedUser = new User((String) check.get("sub"), null, new ArrayList<>());
//
//		} catch (Exception e) {
//			throw new UsernameNotFoundException("JWT token is not valid");
//		}
//
////		List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(parsedUser.getRole());
//
//		return parsedUser;
//	}
//
//	private Map<String, ?> checkToken(String token) {
//		String urlCheckToken = "http://localhost:8002/sso/check";
//		try {
//			MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
//			param.add("token", token);
//			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(param, new HttpHeaders());
//			ResponseEntity<Map> responseEntity = new RestTemplate().postForEntity(urlCheckToken, request, Map.class);
//			return responseEntity.getBody();
//		} catch (HttpStatusCodeException e) {
//			ResponseEntity responseEntity = ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
//					.body(e.getResponseBodyAsString());
//		}
//
//		return null;
//	}
//
//}