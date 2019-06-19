package com.social.server.service;

import com.social.server.dto.RegistrationDto;
import com.social.server.dto.UserDto;
import com.social.server.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService extends CommonService<User, Long> {
    UserDto findByEmail(String email);
    UserDto registerUser(RegistrationDto registrationDto);
    boolean isEmailExist(String email);
    UserDto updateProfile(UserDto userDto);
    UserDto findDtoById(long id);
    List<UserDto> search(long rootUserId, String userName);
    void savePhoto(long userId, MultipartFile file);
    UserDto save(User user);
}
