package desafio.ustore.sso.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.server.ResponseStatusException;

import lombok.Getter;

@Getter
public class BusinessException extends ResponseStatusException{
	private static final long serialVersionUID = 1L;

	public BusinessException(HttpStatus status, String reason) {
		super(status, reason);
	}
	
	public BusinessException(HttpStatus status, @Nullable String reason, @Nullable Throwable cause) {
		super(status, reason, cause);		
	}
	

}
