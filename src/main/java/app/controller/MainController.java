package app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    
    @RequestMapping("*")
    public String run() {
        return "redirect:/frontpage";
    }
}
