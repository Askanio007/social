package com.social.server.service.impl;

import com.social.server.dao.UserRepository;
import com.social.server.dto.UserDto;
import com.social.server.entity.Image;
import com.social.server.entity.User;
import com.social.server.entity.UserDetails;
import com.social.server.http.model.RegistrationModel;
import com.social.server.http.model.UserDetailsModel;
import com.social.server.service.UserService;
import com.social.server.util.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl extends CommonServiceImpl<User, Long, UserRepository> implements UserService {

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        super(userRepository);
    }

    @Override
    public UserDto findByEmail(String email) {
        return UserDto.of(repository.findByEmail(email));
    }

    @Override
    public UserDto findDtoById(long id) {
        return UserDto.of(findById(id));
    }

    @Override
    public UserDto registerUser(RegistrationModel registrationModel) {
        User user = new User();
        user.setEmail(registrationModel.getEmail());
        user.setPassword(registrationModel.getPassword());
        user.setEnable(true);
        user.setName(formatName(registrationModel.getName()));
        user.setSurname(formatName(registrationModel.getSurname()));
        user.getDetails().setSex(registrationModel.getSex());

        return save(user);
    }

    @Override
    public boolean isEmailExist(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public UserDto updateProfile(UserDetailsModel userDetailsModel) {
        User user = findById(userDetailsModel.getId());
        UserDetails details = user.getDetails();

        details.setAbout(userDetailsModel.getAbout());
        details.setBirthday(userDetailsModel.getBirthday());
        details.setCity(userDetailsModel.getCity());
        details.setCountry(userDetailsModel.getCountry());
        details.setPhone(userDetailsModel.getPhone());
        details.setSex(userDetailsModel.getSex());

        user.setName(userDetailsModel.getName());
        user.setSurname(userDetailsModel.getSurname());

        return save(user);
    }

    @Override
    public List<UserDto> search(long rootUserId, String userName) {
        if (StringUtils.isBlank(userName)) {
            return Collections.emptyList();
        }
        String[] nameAndSurname = userName.split(" ");
        if (nameAndSurname.length > 1) {
            return UserDto.of(repository.searchByFullName(rootUserId, formatName(nameAndSurname[0]), formatName(nameAndSurname[1])));
        }
        return UserDto.of(repository.searchByFullName(rootUserId, formatName(nameAndSurname[0]), ""));
    }

    @Override
    public void savePhoto(long userId, MultipartFile file) {
        User user = findById(userId);
        Path filePath = FileUtil.writeFile(file);
        if (filePath != null) {
            user.getDetails().setImage(Image.of(file.getOriginalFilename(), filePath));
            save(user);
        }
    }

    @Override
    public UserDto save(User user) {
        return UserDto.of(repository.save(user));
    }

    private String formatName(String word) {
        if (StringUtils.isBlank(word)) {
            return word;
        }
        word = word.toLowerCase();
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }
}
