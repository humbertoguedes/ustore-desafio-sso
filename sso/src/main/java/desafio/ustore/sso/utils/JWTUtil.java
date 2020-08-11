package desafio.ustore.sso.utils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JWTUtil {

	private static final String SECRET_KEY = "KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK";

	public static String extractID(String token) {
		Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());//Keys.secretKeyFor(SignatureAlgorithm.HS256);
		return extractClaim(token, Claims::getId);
	}
	
	public static String extractUsername(String token) {
		Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());//Keys.secretKeyFor(SignatureAlgorithm.HS256);
//		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
		return extractClaim(token, Claims::getSubject);
	}

	public static Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public static Claims extractAllClaims(String token) {
		Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());//Keys.secretKeyFor(SignatureAlgorithm.HS256);
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}

	private static Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public static String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userDetails.getUsername());
	}

	private static String createToken(Map<String, Object> claims, String subject) {
		Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());//Keys.secretKeyFor(SignatureAlgorithm.HS256);

		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
				.signWith(key).compact();
	}

	public static Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	public static void main(String[] args) {
		
		Key key = Keys.hmacShaKeyFor("KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK".getBytes());
		Key key2 = Keys.hmacShaKeyFor("KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK".getBytes());
//		Keys.secretKeyFor(SignatureAlgorithm.HS256);

		String jws = Jwts.builder()
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
				.setSubject("Joe").signWith(key).compact();
		
		
		if(Jwts.parserBuilder().setSigningKey(key2).build().parseClaimsJws(jws).getBody().getSubject().equals("Joe")) {
			System.out.println("OKOKO");
		}else {
			
			System.out.println("NULL");
		}
		
		
		
	}

}
