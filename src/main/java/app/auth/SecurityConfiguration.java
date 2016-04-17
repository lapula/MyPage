/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

/**
 *
 * @author Sara ja Laur
 */
@Configuration
@EnableWebMvcSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/tervetuloa", "/stats", "/uusiKayttaja", "/css*/**", "/images*/**", "/js*/**").permitAll()
                .antMatchers(HttpMethod.GET, "/nyyttarit/*").permitAll()
                .antMatchers(HttpMethod.POST, "/nyyttarit/*/varaa/*").permitAll()
                .anyRequest().authenticated();

        http.formLogin()
                .loginPage("/tervetuloa")
                .loginProcessingUrl("/authenticate")
                .defaultSuccessUrl("/nyyttarit")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll();

        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/tervetuloa")
                .permitAll()
                .invalidateHttpSession(true);
        
        http.csrf().disable();
    }

    /*@Configuration
    protected static class AuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter {

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {

            auth.inMemoryAuthentication()
                    .withUser("maxwell_smart").password("kenkapuhelin").roles("PASS");
        }
    }*/
    
    
    @Configuration
    protected static class AuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter {

        @Autowired
        private JpaAuthProvider jpaAuthProvider;

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(jpaAuthProvider);
        }
    }
}

