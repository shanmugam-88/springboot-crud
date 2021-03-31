package org.learn.curd.security.jwt.service;

import org.learn.curd.model.User;
import org.learn.curd.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class JwtMyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
         try {
             User user = userRepository.findByEmail(email);
             if (user == null) {
                 throw new UsernameNotFoundException("No user found with username: " + email);
             }
             return user;
         } catch (final Exception e) {
             throw new RuntimeException(e);
         }
    }
}
