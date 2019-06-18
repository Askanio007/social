package com.social.server.service.impl;

import com.social.server.dao.UserRepository;
import com.social.server.dto.RegistrationDto;
import com.social.server.dto.UserDto;
import com.social.server.entity.User;
import com.social.server.entity.UserDetails;
import com.social.server.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

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
        user.setName(registrationDto.getName());
        user.setSurname(registrationDto.getSurname());
        UserDetails userDetails = new UserDetails();
        user.setDetails(userDetails);
        userDetails.setUser(user);

        return UserDto.of(userRepository.save(user));
    }

    @Override
    public UserDto findById(Long id) {
        return UserDto.of(userRepository.findById(id).get());
    }

    @Override
    public boolean isEmailExist(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserDto updateProfile(UserDto userDto) {
        User user = userRepository.findById(userDto.getId()).get();
        UserDetails details = user.getDetails();

        details.setAbout(userDto.getDetails().getAbout());
        details.setBirthday(userDto.getDetails().getBirthday());
        details.setCity(userDto.getDetails().getCity());
        details.setCountry(userDto.getDetails().getCountry());
        details.setPhone(userDto.getDetails().getPhone());
        details.setSex(userDto.getDetails().getSex());

        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());

        return UserDto.of(userRepository.save(user));
    }

    @Override
    public List<UserDto> search(long rootUserId, String userName) {
        if (StringUtils.isBlank(userName)) {
            return Collections.emptyList();
        }
        String[] nameAndSurname = userName.toLowerCase().split(" ");
        if (nameAndSurname.length > 1) {
            return UserDto.of(userRepository.searchByFullName(rootUserId, nameAndSurname[0], nameAndSurname[1]));
        }
        return UserDto.of(userRepository.searchByFullName(rootUserId, nameAndSurname[0], ""));
    }
}
