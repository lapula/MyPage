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
@RequestMapping(value="/game/*")
public class LobbyController {
    
    
    @Autowired
    private HttpServletRequest request;
    
    @Autowired
    private HttpServletResponse response;
    
    @Autowired
    private GameRepository games;
    
    @Autowired
    private PlayerRepository players;
    
    
    @RequestMapping(method = RequestMethod.GET)
    public String getGame(Model model) {
        
        
        String url = request.getRequestURL().toString();
        int i = url.length() - 1;
        String name = "";
        while (url.charAt(i) != '/') {
            name += url.charAt(i);
            i--;
        }
        name = new StringBuilder(name).reverse().toString();
        
        Player p = new Player();
        p.setName("P " + new Random().nextInt(300000));
        
        response.addCookie(new Cookie("Player", "" + p.getId()));
        
        players.save(p);
        
        Game g = null;
        
        if (games.findByName(name) == null) {
            g = new Game();
            g.addPlayer(p);
            g.setName(name);
        } else {
            g = games.findByName(name);
            g.addPlayer(p);
        }

        games.save(g);
        
        
        System.out.println(g.getName());
        System.out.println(g.getPlayers().size());
        System.out.println(p.getName());
        
        return "game";
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/one")
    public String getFrontpage(Model model) {
        
        Cookie c = request.getCookies()[0];
        System.out.println(c.getName());
        
        return "frontpage";
    }
    
      
    @RequestMapping(method = RequestMethod.POST)
    public String add(@ModelAttribute StoryEntry se) {
        
        return "redirect:/frontpage";
    }
    
    @RequestMapping(method = RequestMethod.DELETE)
    public String delete(@RequestParam long storyId) {
        
        return "redirect:/frontpage";
    }
}
