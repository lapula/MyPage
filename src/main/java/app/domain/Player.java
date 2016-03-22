/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author Sara ja Laur
 */

@Entity
public class Player extends AbstractPersistable<Long> {
    
    private String name;
    
    @ManyToOne
    private Game game;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
