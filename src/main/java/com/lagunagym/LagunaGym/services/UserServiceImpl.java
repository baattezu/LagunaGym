package com.lagunagym.LagunaGym.services;

import com.lagunagym.LagunaGym.models.Role;
import com.lagunagym.LagunaGym.models.User;
import com.lagunagym.LagunaGym.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserDetailsService {
    private UserRepository userRepository;
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private User findByUsername(String username){
        return userRepository.findByUsername(username);
    }
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(findByUsername(username));
        User user = findByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("user not found");
        }

        return new org.springframework.security.core.userdetails.
                User(user.getUsername(), user.getPassword(), mapToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapToAuthorities(Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole_title())).collect(Collectors.toList());
    }
}
