package desafio.ustore.sso.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import desafio.ustore.sso.exception.BusinessException;
import desafio.ustore.sso.models.JWTAuthentication;
import desafio.ustore.sso.models.UsernamePasswordAuthentication;
import desafio.ustore.sso.services.TokenService;
import desafio.ustore.sso.services.UserDetailsCustomService;

@RestController
@RequestMapping("/sso")
public class SSOResource {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserDetailsCustomService userDetailsService;

	@Autowired
	TokenService tokenService;
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticate(@RequestBody UsernamePasswordAuthentication authentication) {
		ResponseEntity<?> response;
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authentication.getUsername(),
					authentication.getPassword()));

			UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getUsername());

			String jwtToken = tokenService.createToken(userDetails).getToken();
			response = ResponseEntity.ok(new JWTAuthentication(jwtToken));
		} catch (BusinessException e) {
			response = ResponseEntity.badRequest().body(e);
		}
		return response;
	}

	@RequestMapping(value = "/check")
	public ResponseEntity<?> check(@RequestParam("token") String token) {
		ResponseEntity<?> response;
		Map<String, ?> mapClaims = tokenService.checkToken(token);
		response = ResponseEntity.ok(mapClaims);

		return response;
	}

	@RequestMapping(value = "/logout")
	public ResponseEntity<?> logout(@RequestParam("token") String token) {
		ResponseEntity<?> response;
		try {
			response = ResponseEntity.ok(tokenService.revoke(token));
		} catch (BusinessException e) {
			response = ResponseEntity.badRequest().body(e);
		}
		return response;
	}

}
