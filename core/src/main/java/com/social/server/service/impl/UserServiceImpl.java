package com.social.server.service.impl;

import com.social.server.dao.UserRepository;
import com.social.server.dto.RegistrationDto;
import com.social.server.dto.UserDto;
import com.social.server.entity.User;
import com.social.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto findByEmail(String email) {
        return UserDto.of(userRepository.findByEmail(email));
    }

    @Override
    public UserDto registerUser(RegistrationDto registrationDto) {
        User user = new User();
        user.setEmail(registrationDto.getEmail());
        user.setPassword(registrationDto.getPassword());
        user.setEnable(true);
        return UserDto.of(userRepository.save(user));
    }

    @Override
    public boolean isEmailExist(String email) {
        return userRepository.existsByEmail(email);
    }
}
