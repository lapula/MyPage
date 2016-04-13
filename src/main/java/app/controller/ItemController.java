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
import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Sara ja Laur
 */
@Controller
@RequestMapping(value="/nyyttarit/{id}")
public class ItemController {
    
    
    @Autowired
    private ItemRepository itemRepository;
    
    @Autowired
    private ItemListRepository itemListRepository;

    
    @ModelAttribute
    private Item getItem() {
        return new Item();
    }
    
    
    @RequestMapping(value="/lisaa", method = RequestMethod.POST)
    public String createNewItem(@Valid @ModelAttribute Item item, BindingResult bindingResult, @PathVariable String id) {
        
        if (bindingResult.hasFieldErrors("name") || bindingResult.hasFieldErrors("amount")) {
            System.out.println(bindingResult.getAllErrors().get(0));
            return "tavarat";
        }
        ItemList itemList = itemListRepository.findById(id);
        item.setItemList(itemList);
        item.setReserved(0);
        item.setReservedBy(new HashMap<>());
        item.setId(null);
        item = itemRepository.save(item);
        
        itemList.getItems().add(item);
        itemListRepository.save(itemList);

        return "redirect:/nyyttarit/" + id;
    }
    
    @RequestMapping(value="/varaa/{itemId}", method = RequestMethod.POST)
    public String makeReservations(@PathVariable String id, @PathVariable Long itemId, 
            @RequestParam("reserveAmount") Integer reserveAmount,
            @RequestParam("reserverName") String reserverName) {
        
        Item item = itemRepository.findOne(itemId);
        item.getReservedBy().put(reserverName, reserveAmount);
        itemRepository.save(item);

        return "redirect:/nyyttarit/" + id;
    }
    
    @RequestMapping(value = "/delete/{itemId}", method = RequestMethod.POST)
    public String deleteItemList(@PathVariable String id, @PathVariable Long itemId ) {
        
        itemRepository.delete(itemId);
        
        return "redirect:/nyyttarit/" + id;
    }
    
}
