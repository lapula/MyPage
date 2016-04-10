/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author Sara ja Laur
 */
@Entity
public class ItemList extends AbstractPersistable<Long> {
    
    @OneToMany(mappedBy = "itemList", fetch = FetchType.LAZY)
    @Column(name="items")
    private List<Item> items;

    @ManyToOne
    @Column(name="user")
    @JoinColumn(name="id")
    private User user;
    
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
    
    public void addItem(Item item) {
        items.add(item);
    }
    
    
    
}
