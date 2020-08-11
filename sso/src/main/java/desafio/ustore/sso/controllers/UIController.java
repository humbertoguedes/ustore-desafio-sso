package desafio.ustore.sso.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UIController {
	
	@RequestMapping(value = "/secure")
    public String loadUI() {
        return "secure";
    }

}
