package desafio.ustore.ssocommons;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public interface ISSOResource {
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticate(@RequestBody UsernamePasswordAuthentication authentication);
	
	@RequestMapping(value = "/check")
	@ResponseBody
	public Map<String, ?> check(@RequestParam("token") String token);
	
	@RequestMapping(value = "/logout")
	@ResponseBody
	public String logout(@RequestParam("token") String token);

}
