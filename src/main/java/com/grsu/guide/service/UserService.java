package com.grsu.guide.service;

import com.grsu.guide.domain.User;
import com.grsu.guide.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(User user){
        userRepository.save(user);
    }
}
