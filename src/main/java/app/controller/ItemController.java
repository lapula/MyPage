/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller;

import app.domain.Item;
import app.domain.ItemList;
import app.domain.Person;
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
import app.repository.PersonRepository;
import app.repository.ReservationRepository;
import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Sara ja Laur
 */
@Controller
@RequestMapping(value="/nyyttarit/{id}")
public class ItemController {
    
    @Autowired
    private PersonRepository personRepository;
    
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
    
    private Long getPersonId() {
        String personName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person person = personRepository.findByUsername(personName);
        return person.getId();
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
            return "tavarat";
        }
        ItemList itemList = itemListRepository.findById(id);
        if (!itemList.getPerson().getId().equals(getPersonId())) {
            throw new IllegalAccessError();
        }
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
            @RequestParam("reserverName") String reserverName,
            RedirectAttributes redirectAttributes) {
        
        Item item = itemRepository.findOne(itemId);
        Reservation reservation = new Reservation();
        reservation.setAmount(reserveAmount);
        reservation.setName(reserverName);
        reservation.setItem(item);
        reservation = reservationRepository.save(reservation);
        
        item.getReservedBy().add(reservation);
        item.setReserved(item.getReserved()+ reserveAmount);
        item = itemRepository.save(item);
        
        redirectAttributes.addFlashAttribute("lastReservation", reservation.getId());
        redirectAttributes.addFlashAttribute("lastReservationItem", item.getId());
        
        return "redirect:/nyyttarit/" + id;
    }
    
    @RequestMapping(value = "/deleteComment", method = RequestMethod.POST)
    public String deleteComment(@PathVariable String id, @RequestParam String comment ) {
        
        ItemList itemList = itemListRepository.findById(id);
        if (!itemList.getPerson().getId().equals(getPersonId())) {
            throw new IllegalAccessError();
        }
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
    
    @RequestMapping(value = "/deleteReservation/{itemId}/reservation/{reservationId}", method = RequestMethod.POST)
    public String deleteItemReservation(@PathVariable String id, @PathVariable Long itemId, @PathVariable Long reservationId) {
        
        Reservation reservation = reservationRepository.findOne(reservationId);
        Item item = itemRepository.findOne(itemId);
        int index = 0;
        int mem = -1;
        
        for (Reservation r : item.getReservedBy()) {
            if (r.getId().equals(reservationId)) {
                mem = index;
            }
            index++;
        }
        
        if (mem != -1) {
            item.getReservedBy().remove(mem);
        }
        item.setReserved(item.getReserved() - reservation.getAmount());
        itemRepository.save(item);
        reservationRepository.delete(reservation);
        
        return "redirect:/nyyttarit/" + id;
    }
    
    @RequestMapping(value = "/delete/{itemId}", method = RequestMethod.POST)
    public String deleteItemList(@PathVariable String id, @PathVariable Long itemId ) {
        
        ItemList itemList = itemListRepository.findById(id);
        if (!itemList.getPerson().getId().equals(getPersonId())) {
            throw new IllegalAccessError();
        }
        
        Item item = itemRepository.findOne(itemId);
        reservationRepository.delete(item.getReservedBy());
        itemRepository.delete(item);
        
        return "redirect:/nyyttarit/" + id;
    }
    
}
