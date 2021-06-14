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

    public void Message(String userName, String email, String message){
        User user = new User(userName,email,message);
        userRepository.save(user);
    }
}
