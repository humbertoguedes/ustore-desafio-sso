//package desafio.ustore.appshop.configs;
//
//import java.io.IOException;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.security.authentication.InsufficientAuthenticationException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
//import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
//
//public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
//
//	protected JwtAuthenticationFilter() {
//		super("/**");
//	}
//
////	@Override
////	protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
////		return true;
////	}
//
//	@Override
//	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
//			throws AuthenticationException, IOException, ServletException {
//		String header = request.getHeader("Authorization");
//
//		if (header == null || !header.startsWith("Bearer ")) {
//			throw new InsufficientAuthenticationException("No JWT token found in request headers");
//		}
//
//		String authToken = header.substring(7);
//
//		return getAuthenticationManager().authenticate(new PreAuthenticatedAuthenticationToken(authToken, null));
//	}
//}