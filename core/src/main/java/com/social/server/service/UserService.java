package com.social.server.service;

import com.social.server.dto.RegistrationDto;
import com.social.server.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    UserDto findByEmail(String email);
    UserDto registerUser(RegistrationDto registrationDto);
    boolean isEmailExist(String email);
    UserDto findById(Long id);
    UserDto updateProfile(UserDto userDto);
    List<UserDto> search(long rootUserId, String userName);
    void savePhoto(long userId, MultipartFile file);
}
