/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller;

import app.domain.ItemList;
import app.domain.Person;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import app.repository.PersonRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Sara ja Laur
 */
@Controller
@RequestMapping(value="/uusiKayttaja")
public class NewUserController {
    
    
    @Autowired
    private PersonRepository userRepository;
    
    
    
    @RequestMapping(method = RequestMethod.POST)
    public String createNewUser(@Valid @ModelAttribute Person user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        
        if (bindingResult.hasErrors()) {
            System.out.println(user.getPassword());
            System.out.println(bindingResult.getAllErrors().get(0));
            return "uusiKayttaja";
        }
        user.setItems(new ArrayList<>());
        userRepository.save(user);
        redirectAttributes.addFlashAttribute("newUserCreated", "Kirjaudu sisään!");
        return "redirect:/tervetuloa";
    }
    
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public String deleteUser(@PathVariable Long id ) {
        
        userRepository.delete(id);
        
        return "redirect:/logout";
    }
}
