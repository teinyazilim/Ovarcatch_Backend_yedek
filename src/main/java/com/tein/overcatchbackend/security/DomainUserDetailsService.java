package com.tein.overcatchbackend.security;

import com.tein.overcatchbackend.domain.model.User;
import com.tein.overcatchbackend.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Authenticate a user from the database.
 */
@Slf4j
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {
   // private final Logger log = Logger.getLogger(DomainUserDetailsService.class);

    private final UserRepository userRepository;


    public DomainUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email) ;//.orElseThrow(() -> new AppException("User not found."));
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }

        Set<GrantedAuthority> grantedAuthoritySet = new HashSet<>();
        grantedAuthoritySet.add(new SimpleGrantedAuthority("ROLE_ANONYMOUS"));
        user.getRoles().forEach(role -> {
            //authorities.add(new SimpleGrantedAuthority(role.getName()));
            grantedAuthoritySet.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleCode()));
        });

        //
        log.info("User Details loaded for user :" + user.getEmail());
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthoritySet);

        // return new org.springframework.security.core.userdetails.User(user.getUsername(),"" , grantedAuthoritySet);
    }
}
