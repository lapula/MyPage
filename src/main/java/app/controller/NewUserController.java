/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller;

import app.domain.User;
import app.repository.UserRepository;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Sara ja Laur
 */
@Controller
@RequestMapping(value="/uusiKayttaja")
public class NewUserController {
    
    
    @Autowired
    private UserRepository userRepository;
    
    @RequestMapping(method = RequestMethod.GET)
    public String getNewUserPage(Model model) {
        
        //model.addAttribute("users", userRepository.findAll());
        model.addAttribute("user", new User());
        
        return "uusiKayttaja";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String createNewUser(@Valid @ModelAttribute User user, BindingResult bindingResult) {
        
        if (bindingResult.hasErrors()) {
            return "uusiKayttaja";
        }
        
        userRepository.save(user);
        
        
        return "redirect:/tervetuloa";
    }
}
