package app.controller;

import app.domain.Item;
import app.domain.ItemList;
import app.domain.Person;
import app.repository.ItemListRepository;
import app.repository.ItemRepository;
import app.repository.PersonRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(method = RequestMethod.GET)
public class MainController {

    @Autowired
    private ItemListRepository itemListRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ItemRepository itemRepository;

    @ModelAttribute("loggedUser")
    public Person getLoggedUser() {
        String personName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person loggedUser = personRepository.findByUsername(personName);
        return loggedUser;
    }

    @RequestMapping(value = "/tapahtumat/{id}")
    public String getItems(Model model, @PathVariable String id) {

        ItemList itemList = itemListRepository.findById(id);
        List<Item> items = itemList.getItems();
        Collections.sort(items);
        
        model.addAttribute("items", items);
        model.addAttribute("item", new Item());
        model.addAttribute("itemList", itemList);
        model.addAttribute("itemListCreatorId", itemList.getPerson().getId());

        
        return "tavarat";
    }

    @RequestMapping(value = "/tapahtumat")
    public String getItemLists(Model model) {

        Person person = getLoggedUser();
        model.addAttribute("itemList", new ItemList());
        model.addAttribute("itemLists", person.getItems());

        return "tapahtumat";
    }

    @RequestMapping(value = "/uusiKayttaja")
    public String getNewUserPage(Model model) {

        model.addAttribute("users", personRepository.findAll());
        model.addAttribute("person", new Person());

        return "uusiKayttaja";
    }
    
    @RequestMapping(value = "/stats")
    public String getStatsPage(Model model) {

        model.addAttribute("users", personRepository.findAll());
        model.addAttribute("person", new Person());

        return "stats";
    }

    @RequestMapping(value = "tervetuloa", method = RequestMethod.GET)
    public String welcome(Model model) {
        model.addAttribute("person", new Person());
        return "tervetuloa";
    }

    @RequestMapping(value = "*")
    public String redirectWelcome() {
        if (getLoggedUser() != null) {
            return "redirect:/tapahtumat";
        }
        return "redirect:/tervetuloa";
    }
}
