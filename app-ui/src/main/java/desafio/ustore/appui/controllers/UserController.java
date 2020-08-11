//package desafio.ustore.appui.controllers;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.ModelAndView;
//
//@Controller
//public class UserController {
//
//	@Autowired
//	private UserApi userApi;
//
//	@GetMapping("/")
//	public ModelAndView findAll() {
//
//		ModelAndView mv = new ModelAndView("/user");
//		mv.addObject("users", userApi.all());
//
//		return mv;
//	}
//
//	@GetMapping("/add")
//	public ModelAndView add(User user) {
//
//		ModelAndView mv = new ModelAndView("/formUser");
//		mv.addObject("user", user);
//
//		return mv;
//	}
//
//	@GetMapping("/edit/{id}")
//	public ModelAndView edit(@PathVariable("id") Integer id) {
//		return add(userApi.load(id));
//	}
//
//	@PostMapping("/save")
//	public ModelAndView save(User user, BindingResult result) {
//		userApi.create(user);
//
//		return findAll();
//	}
//
//	@FeignClient(name = "users", url = "http://localhost:8010/")
//	@RequestMapping("/user")
//	interface UserApi {
//
//		@GetMapping
//		public ResponseEntity<?> all();
//
//		@PostMapping
//		public ResponseEntity<?> create(@RequestBody User user);
//
//		@GetMapping("/{id}")
//		public User load(@PathVariable(name = "id") Integer id);
//
//		@PutMapping("/{id}")
//		public ResponseEntity<?> edit(@PathVariable(name = "id") Integer id,
//				@RequestBody User userModified);
//
//		@DeleteMapping("/{id}")
//		public ResponseEntity<?> delete(@PathVariable(name = "id") Integer id);
//
//	}
//
//}
