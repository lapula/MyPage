/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller;

import app.domain.Game;
import app.domain.Player;
import app.domain.StoryEntry;
import app.repository.GameRepository;
import app.repository.PlayerRepository;
import app.repository.StoryEntryRepository;
import java.util.List;
import java.util.Random;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Sara ja Laur
 */
@Controller
@RequestMapping(value="/clean")
public class LobbyController {
    
    
    @Autowired
    private HttpServletRequest request;
    
    @Autowired
    private HttpServletResponse response;
    
    @Autowired
    private GameRepository games;
    
    @Autowired
    private PlayerRepository players;
    
    @Autowired
    StoryEntryRepository stories;
    
    
   
    
    @RequestMapping(method = RequestMethod.GET)
    public String getFrontpage(Model model) {
        
        stories.deleteAll();
        players.deleteAll();
        games.deleteAll();
        
        return "frontpage";
    }
    
      
    
}
