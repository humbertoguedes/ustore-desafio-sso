package desafio.ustore.sso.services;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import desafio.ustore.sso.exception.BusinessException;
import desafio.ustore.sso.models.AuthUserDetail;
import desafio.ustore.sso.models.Token;
import desafio.ustore.sso.models.User;
import desafio.ustore.sso.repository.TokenRepository;
import desafio.ustore.sso.repository.UserRepository;
import desafio.ustore.sso.utils.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

@Service
public class TokenService {

	@Autowired
	TokenRepository tokenRepository;

	@Autowired
	UserRepository userDetailRepository;

	public Token createToken(UserDetails userDetails) throws BusinessException {
		Optional<Token> tokenOP = tokenRepository.findByUsername(userDetails.getUsername());

		if (tokenOP.isPresent()) {
			Token token = tokenOP.get();

			try {
				if (JWTUtil.validateToken(token.getToken(), userDetails)) {
					return tokenOP.get();
				}
			} catch (JwtException e) {
				tokenRepository.delete(token);
			}

		}

		String jwtToken = JWTUtil.generateToken(userDetails);
		Token token = new Token();
		token.setToken(jwtToken);
		token.setUsername(userDetails.getUsername());
		token.setExpiration(JWTUtil.extractExpiration(jwtToken).getTime());
		return tokenRepository.save(token);
	}

	public Map<String, ?> checkToken(String token) {
		Map map = null;
		try {

			String username = JWTUtil.extractUsername(token);
			Optional<Token> tokenOp = tokenRepository.findByUsername(username);
			Optional<User> userDetailsOp = userDetailRepository.findByUsername(username);
			AuthUserDetail userDetails = new AuthUserDetail(userDetailsOp.orElse(new User()));

			if (JWTUtil.validateToken(token, userDetails)) {
				map = JWTUtil.extractAllClaims(token);
				map.put("active", Boolean.TRUE);
			} else {
				if (tokenOp.isPresent()) {
					tokenRepository.delete(tokenOp.get());
					map = JWTUtil.extractAllClaims(tokenOp.get().getToken());
					map.put("active", Boolean.FALSE);
				}
			}
		} catch (ExpiredJwtException e) {
			throw new BusinessException(HttpStatus.BAD_GATEWAY, "Token Expirado", e);
		}

		return map;
	}

	public Boolean revoke(String token) throws BusinessException {
		try {
			String username = JWTUtil.extractUsername(token);
			Optional<Token> tokenOp = tokenRepository.findByUsername(username);

			if (tokenOp.isPresent()) {
				tokenRepository.delete(tokenOp.get());
				return true;
			}
		} catch (JwtException e) {

		}

		return false;

	}

}
