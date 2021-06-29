package com.grsu.guide.service;

import com.grsu.guide.domain.User;
import com.grsu.guide.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService{

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByUserName(String name){
        return userRepository.findByUserName(name);
    }

    public List<User> findAllUsers() {return (List<User>) userRepository.findAll();}

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUserName(s);
    }

    public void saveUser(User user){ userRepository.save(user);}

    public void deleteUser(Long id){ userRepository.deleteById(id);}

    public User getUser(Long id){ return userRepository.findUserById(id);}
}
