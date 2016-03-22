/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author Sara ja Laur
 */

@Entity
public class Game extends AbstractPersistable<Long> {
    
    private String name;
    
    @OneToMany
    private List<Player> players;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
    
    
    public void addPlayer(Player p) {
        
        if (this.players == null) {
            this.players = new ArrayList<>();
        }
        
        players.add(p);
    }

    
    
}
