/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Sara ja Laur
 */
@Entity
public class Item implements Serializable {

    @ManyToOne
    private ItemList itemList;

    @Id
    @Column(name = "item_id")
    @GeneratedValue
    private Long id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "amount")
    private Integer amount;
    
    @Column(name = "reserved")
    private Integer reserved;
    
    @ElementCollection
    @Column(name = "reservedBy")
    private Map<String, Integer> reservedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ItemList getItemList() {
        return itemList;
    }

    public void setItemList(ItemList itemList) {
        this.itemList = itemList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getReserved() {
        return reserved;
    }

    public void setReserved(Integer reserved) {
        this.reserved = reserved;
    }

    public Map<String, Integer> getReservedBy() {
        return reservedBy;
    }

    public void setReservedBy(Map<String, Integer> reservedBy) {
        this.reservedBy = reservedBy;
    }

}
