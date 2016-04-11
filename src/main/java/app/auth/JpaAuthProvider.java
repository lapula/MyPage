/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.auth;

import app.domain.Person;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import app.repository.PersonRepository;

/**
 *
 * @author Sara ja Laur
 */
@Component
public class JpaAuthProvider implements AuthenticationProvider {
    
    @Autowired
    private PersonRepository userRepository;
    
    @Override
    public Authentication authenticate(Authentication a) throws AuthenticationException {
        String username = a.getPrincipal().toString();
        String password = a.getCredentials().toString();

        Person user = userRepository.findByUsername(username);

        if (user == null) {
            throw new AuthenticationException("Unable to authenticate user " + username) {
            };
        }

        if (!BCrypt.hashpw(password, user.getSalt()).equals(user.getPassword())) {
            throw new AuthenticationException("Unable to authenticate user " + username) {
            };
        }

        List<GrantedAuthority> grantedAuths = new ArrayList<>();
        grantedAuths.add(new SimpleGrantedAuthority("USER"));

        return new UsernamePasswordAuthenticationToken(user.getUsername(), password, grantedAuths);
    }

    @Override
    public boolean supports(Class<?> type) {
        return true;
    }
    
}