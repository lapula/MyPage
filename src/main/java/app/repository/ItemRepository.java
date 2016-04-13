/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.repository;

import app.domain.Item;
import app.domain.ItemList;
import app.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Sara ja Laur
 */
public interface ItemRepository extends JpaRepository<Item, Long>{
    
}
