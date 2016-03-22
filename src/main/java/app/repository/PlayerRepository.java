/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.repository;

import app.domain.Player;
import app.domain.StoryEntry;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Sara ja Laur
 */
public interface PlayerRepository extends JpaRepository<Player, Long>{
    
}
