/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller;

import app.domain.Item;
import app.domain.ItemList;
import app.domain.Reservation;
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
import app.repository.ReservationRepository;
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
    
    @Autowired
    private ReservationRepository reservationRepository;

    
    @ModelAttribute
    private Item getItem() {
        return new Item();
    }
    
    @RequestMapping(value="lisaaKommentti", method = RequestMethod.POST)
    public String addCommentToItemList(@PathVariable String id, @RequestParam String comment) {
        
        ItemList itemList = itemListRepository.findById(id);
        itemList.getComments().add(comment);
        itemListRepository.save(itemList);
        
        return "redirect:/nyyttarit/" + id;
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
        item.setReservedBy(new ArrayList<>());
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
        Reservation reservation = new Reservation();
        reservation.setAmount(reserveAmount);
        reservation.setName(reserverName);
        reservation.setItem(item);
        reservationRepository.save(reservation);
        
        item.getReservedBy().add(reservation);
        item.setReserved(item.getReserved()+ reserveAmount);
        item = itemRepository.save(item);
        
        return "redirect:/nyyttarit/" + id;
    }
    
    @RequestMapping(value = "/deleteComment", method = RequestMethod.POST)
    public String deleteComment(@PathVariable String id, @RequestParam String comment ) {
        
        ItemList itemList = itemListRepository.findById(id);
        int index = 0;
        int mem = -1;
        for (String c : itemList.getComments()) {
            if (c.equals(comment)) {
                mem = index;
            }
            index++;
        }
        
        if (mem != -1) {
            itemList.getComments().remove(mem);
        }
        
        itemListRepository.save(itemList);
        
        return "redirect:/nyyttarit/" + id;
    }
    
    @RequestMapping(value = "/delete/{itemId}", method = RequestMethod.POST)
    public String deleteItemList(@PathVariable String id, @PathVariable Long itemId ) {
        
        Item item = itemRepository.findOne(itemId);
        reservationRepository.delete(item.getReservedBy());
        itemRepository.delete(item);
        
        return "redirect:/nyyttarit/" + id;
    }
    
}
