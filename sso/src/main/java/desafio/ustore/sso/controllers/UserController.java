package desafio.ustore.sso.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import desafio.ustore.sso.models.User;
import desafio.ustore.sso.services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/")
	public ModelAndView findAll() {

		ModelAndView mv = new ModelAndView("/listUser");
		mv.addObject("users", userService.findAll());

		return mv;
	}

	@GetMapping("/add")
	public ModelAndView add(User user) {

		ModelAndView mv = new ModelAndView("/formUser");
		mv.addObject("user", user);

		return mv;
	}

	@GetMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable("id") Integer id) {
		return add(userService.load(id));
	}

	@PostMapping("/save")
	public ModelAndView save(User user, BindingResult result) {

//		if (result.hasErrors()) {
//			return add(user);
//		}

		userService.create(user);

		return findAll();
	}

}
