package desafio.ustore.sso.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import desafio.ustore.sso.models.User;
import desafio.ustore.sso.services.UserService;

@RestController
@RequestMapping("/user1")
public class UserResource {

	@Autowired
	private UserService userService;
	
	@GetMapping
	public ResponseEntity<?> all(){
		return ResponseEntity.ok(userService.findAll());
	}
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody User user){
		return ResponseEntity.ok(userService.create(user));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> load(@PathVariable(name = "id") Integer id){
		return ResponseEntity.ok(userService.load(id));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> edit(@PathVariable(name = "id") Integer id, @RequestBody User userModified){
		return ResponseEntity.ok(userService.edit(id, userModified));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(name = "id") Integer id){
		return ResponseEntity.ok(userService.delete(id));
	}
	
}
