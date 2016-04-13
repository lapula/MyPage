/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller;

import app.domain.Item;
import app.domain.ItemList;
import app.domain.Person;
import app.repository.ItemListRepository;
import app.repository.ItemRepository;
import app.repository.PersonRepository;
import java.util.ArrayList;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Sara ja Laur
 */
@Controller
@RequestMapping(value="/nyyttarit")
public class ItemListController {
    
    @Autowired
    private ItemListRepository itemListRepository;
    
    @Autowired
    private PersonRepository personRepository;
    
    @Autowired
    private ItemRepository itemRepository;

    
    
    
    
    @RequestMapping(method = RequestMethod.POST)
    public String createNewUser(@Valid @ModelAttribute ItemList itemList, BindingResult bindingResult) {
        
        if (bindingResult.hasErrors()) {
            return "nyyttarit";
        }
        
        String personName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person person = personRepository.findByUsername(personName);
        
        itemList.setItems(new ArrayList<>());
        itemList.setPerson(person);
        
        itemList = itemListRepository.save(itemList);
        person.getItems().add(itemList);
        personRepository.save(person);
        
        return "redirect:/nyyttarit";
    }
    
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String deleteItemList(@PathVariable Long id ) {
        
        ItemList itemList = itemListRepository.findOne(id);
        
        for (Item item : itemList.getItems()) {
            itemRepository.delete(item.getId());
        }
        
        itemListRepository.delete(id);
        
        return "redirect:/nyyttarit";
    }
    
}
