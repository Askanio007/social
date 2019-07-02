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
import com.social.server.util.ImageUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl extends CommonServiceImpl<User, Long, UserRepository> implements UserService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        super(userRepository);
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto findBy(long id) {
        return UserDto.of(getById(id));
    }

    @Override
    public UserDto registerUser(RegistrationModel registrationModel) {
        User user = User.builder()
                .email(registrationModel.getEmail())
                .password(passwordEncoder.encode(registrationModel.getPassword()))
                .name(formatName(registrationModel.getName()))
                .surname(formatName(registrationModel.getSurname()))
                .sex(registrationModel.getSex())
                .create();
        return save(user);
    }

    @Override
    public boolean isEmailExist(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public UserDto updateProfile(UserDetailsModel userDetailsModel) {
        User user = getById(userDetailsModel.getId());
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
    public String savePhoto(long userId, MultipartFile file) {
        User user = getById(userId);
        Path filePath = FileUtil.writeFile(file);
        if (filePath != null) {
            user.getDetails().setImage(Image.of(file.getOriginalFilename(), filePath));
            user = repository.save(user);
            return ImageUtil.convertImageTo64encode(user.getImage());
        }
        return null;
    }

    @Override
    public UserDto save(User user) {
        return UserDto.of(repository.save(user));
    }

    @Override
    public UserDto findBy(String email, String password) {
        User user = repository.findByEmailAndPassword(email, passwordEncoder.encode(password));
        return user == null ? null : UserDto.of(user);
    }

    private String formatName(String word) {
        if (StringUtils.isBlank(word)) {
            return word;
        }
        word = word.toLowerCase();
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }
}
