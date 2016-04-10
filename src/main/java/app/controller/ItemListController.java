/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller;

import app.domain.Item;
import app.repository.ItemListRepository;
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
@RequestMapping(value="/tavarat")
public class ItemListController {
    
    @Autowired
    private ItemListRepository itemListRepository;
    
    @RequestMapping(method = RequestMethod.GET)
    public String getItems(Model model) {
        
        model.addAttribute("items", itemListRepository.findAll());
        
        return "items";
    }
    
    
    
}
