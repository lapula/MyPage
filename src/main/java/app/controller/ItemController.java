/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller;

import app.domain.Item;
import app.domain.ItemList;
import app.repository.ItemListRepository;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import app.repository.ItemRepository;

/**
 *
 * @author Sara ja Laur
 */
@Controller
@RequestMapping(value="/nyyttarit/{id}")
public class ItemController {
    
    
    @Autowired
    ItemRepository itemRepository;
    
    @Autowired
    ItemListRepository itemListRepository;

    
    
    
    @RequestMapping(value="/lisaa", method = RequestMethod.POST)
    public String createNewUser(@Valid @ModelAttribute Item item, BindingResult bindingResult, @PathVariable Long id) {
        
        if (bindingResult.hasErrors()) {
            return "tavarat";
        }
        ItemList itemList = itemListRepository.findOne(id);
        item.setItemList(itemList);
        item.setId(null);
        System.out.println("ITEM ID " + item.getId());
        item = itemRepository.save(item);
        System.out.println("ITEM ID NOW" + item.getId());
        System.out.println("ITEM NAME " + item.getName());
        
        
        
        
        itemList.getItems().add(item);
        itemListRepository.save(itemList);

        return "redirect:/nyyttarit/" + id;
    }
    
    @RequestMapping(value = "/delete/{itemId}", method = RequestMethod.POST)
    public String deleteItemList(@PathVariable Long id, @PathVariable Long itemId ) {
        
        itemRepository.delete(itemId);
        
        return "redirect:/nyyttarit/" + id;
    }
    
}
