package app.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }
    
    @RequestMapping(value = "createUser", method = RequestMethod.GET)
    public String createUser() {
        return "uusiKayttaja";
    }
    
    @RequestMapping(value= "", method = RequestMethod.GET)
    public String redirectWelcome() {
        return "redirect:/tervetuloa";
    }
    
    @RequestMapping(value = "tervetuloa", method = RequestMethod.GET)
    public String welcome(Model model) {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        
        model.addAttribute("user", name);
        
        return "tervetuloa";
    }
}
