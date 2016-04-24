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
import app.repository.ItemRepository;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import app.repository.PersonRepository;
import app.repository.ReservationRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Sara ja Laur
 */
@Controller
@RequestMapping(value = "/uusiKayttaja")
public class NewUserController {

    @Autowired
    private PersonRepository userRepository;
    
    @Autowired
    private ItemListRepository itemListRepository;
    
    @Autowired
    private ItemRepository itemRepository;
    
    @Autowired
    private ReservationRepository reservationRepository;

    @RequestMapping(method = RequestMethod.POST)
    public String createNewUser(Model model, @Valid @ModelAttribute Person user, BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "uusiKayttaja";
        } else if (userRepository.findByUsername(user.getUsername()) != null) {
            model.addAttribute("errorMessage", "Käyttäjänimi on jo käytössä!");
            return "uusiKayttaja";
        } else if (!user.getPassword().equals(user.getPasswordAgain())) {
            model.addAttribute("errorMessage", "Salasanat eivät täsmänneet!");
            return "uusiKayttaja";
        } else if (user.getPassword().length() < 4) {
            model.addAttribute("errorMessage", "Salasanan kuuluu olla vähintään 4 merkkiä pitkä!");
            return "uusiKayttaja";
        }
        
        user.setPasswordAgain("0000");
        user.setItems(new ArrayList<>());
        user.setSaltedPassword();
        
        // Add example ItemList
        ItemList itemList = new ItemList();
        itemList.setComments(new ArrayList<>());
        itemList.getComments().add("Tähän sinä tai käyttäjä voi jättää kommentteja, esimerkiksi jos haluaa tuoda jotain muuta tai ilmoittaa tulevansa myöhässä.");
        itemList.setName("Esimerkkitapahtuma");
        itemList.setPerson(user);
        itemList.setDescription("TBD");
        itemList.setItems(new ArrayList<>());
        user.getItems().add(itemList);
        
        // Add example items
        Item item = new Item();
        item.setName("Karkkipusseja");
        item.setAmount(3);
        item.setReservedBy(new ArrayList<>());
        item.setItemList(itemList);
        item.setReserved(0);
        
        Item item2 = new Item();
        item2.setName("Jaffapullo 1,5 litraa");
        item2.setAmount(1);
        item2.setReservedBy(new ArrayList<>());
        item2.setItemList(itemList);
        item2.setReserved(0);
        
        itemList.getItems().add(item);
        itemList.getItems().add(item2);
        
        // Add reservations
        Reservation reservation = new Reservation();
        reservation.setName("Pekka & Seppo");
        reservation.setAmount(2);
        reservation.setItem(item);
        item.getReservedBy().add(reservation);
        item.setReserved(2);
        
        userRepository.save(user);
        itemListRepository.save(itemList);
        itemRepository.save(item);
        itemRepository.save(item2);
        reservationRepository.save(reservation);
        
        redirectAttributes.addFlashAttribute("newUserCreated", "Kirjaudu sisään!");
        return "redirect:/tervetuloa";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateUser(@RequestParam(required = false) String username,
            @RequestParam(required = false) String password, @RequestParam(required = false) String passwordAgain,
            RedirectAttributes redirectAttributes) {

        String personName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person updatedUser = userRepository.findByUsername(personName);

        // TO IMPLEMENT USERNAME CHANGE PASSWORD MUST BE MANDATORY

        if (username != null && !username.equals("")) {
            
            updatedUser.setUsername(username);

            if (userRepository.findByUsername(username) != null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Käyttäjänimi on jo käytössä!");
                return "redirect:/tervetuloa";
            }
        }
        if (password != null && !password.equals("")) {

            updatedUser.setPassword(password);
            updatedUser.setPasswordAgain(password);

            if (!updatedUser.getPassword().equals(updatedUser.getPasswordAgain())) {
                redirectAttributes.addFlashAttribute("errorMessage", "Salasanat eivät täsmänneet!");
                return "redirect:/tervetuloa";
            } else if (updatedUser.getPassword().length() < 4) {
                redirectAttributes.addFlashAttribute("errorMessage", "Salasanan kuuluu olla vähintään 4 merkkiä pitkä!");
                return "redirect:/tervetuloa";
            }
        }

        updatedUser.setPasswordAgain("0000");
        updatedUser.setSaltedPassword();

        userRepository.save(updatedUser);
        redirectAttributes.addFlashAttribute("userUpdated", "Tiedot päivitetty!");
        return "redirect:/tervetuloa";
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public String deleteUser(@PathVariable Long id) {
        
        Person user = userRepository.findOne(id);
        userRepository.delete(user);

        return "redirect:/logout";
    }
}
