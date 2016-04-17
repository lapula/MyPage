/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 *
 * @author Sara ja Laur
 */
@Entity
public class Person implements Serializable {

    @Id
    @Column(name = "user_id")
    @GeneratedValue
    private Long id;
    
    
    @Column(name="username", unique = true)
    @NotBlank
    @Length(min = 2, max = 32)
    private String username;
    
    @Column(name="password")
    private String password;
    
    @Column(name="passwordAgain")
    private String passwordAgain;
    
    @Column(name="salt")
    private String salt;
    

    @OneToMany(mappedBy = "person")
    private List<ItemList> items;

    public String getPasswordAgain() {
        return passwordAgain;
    }

    public void setPasswordAgain(String passwordAgain) {
        this.passwordAgain = passwordAgain;
    }
    
    
    
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setSaltedPassword() {
        this.salt = BCrypt.gensalt();
        this.password = BCrypt.hashpw(password, this.salt);
    }
    
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ItemList> getItems() {
        return items;
    }

    public void setItems(List<ItemList> items) {
        this.items = items;
    }
    
    
}
