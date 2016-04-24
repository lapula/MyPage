/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.domain;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Sara ja Laur
 */
@Entity
public class Item implements Serializable, Comparable<Item> {

    @ManyToOne
    private ItemList itemList;

    @Id
    @Column(name = "item_id")
    @GeneratedValue
    private Long id;
    
    @Column(name = "name")
    @NotBlank
    private String name;
    
    @Column(name = "amount")
    @NotNull
    private Integer amount;
    
    @Column(name = "reserved")
    private Integer reserved;
    
    @Column(name = "reservedBy")
    @OneToMany(mappedBy = "item", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Reservation> reservedBy;

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

    public List<Reservation> getReservedBy() {
        return reservedBy;
    }

    public void setReservedBy(List<Reservation> reservedBy) {
        this.reservedBy = reservedBy;
    }

    @Override
    public int compareTo(Item t) {
        if (this.getId() < t.getId()) {
            return 1;
        } else if (this.getId() > t.getId()) {
            return -1;
        } else {
            return 0;
        }
    }

    

}
