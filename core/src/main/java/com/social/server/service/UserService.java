package com.social.server.service;

import com.social.server.dto.RegistrationDto;
import com.social.server.dto.UserDto;

public interface UserService {
    UserDto findByEmail(String email);
    UserDto registerUser(RegistrationDto registrationDto);
    boolean isEmailExist(String email);
    UserDto findById(Long id);
    UserDto updateProfile(UserDto userDto);
}
