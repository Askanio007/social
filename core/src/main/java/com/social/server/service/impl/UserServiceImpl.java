package com.social.server.service.impl;

import com.social.server.dao.UserRepository;
import com.social.server.dto.UserDto;
import com.social.server.entity.User;
import com.social.server.entity.UserDetails;
import com.social.server.http.model.RegistrationModel;
import com.social.server.http.model.RestorePasswordModel;
import com.social.server.http.model.UserDetailsModel;
import com.social.server.service.PhotoSaver;
import com.social.server.service.UserService;
import com.social.server.service.transactional.ReadTransactional;
import com.social.server.service.transactional.WriteTransactional;
import com.social.server.validator.PasswordValidator;
import com.social.server.validator.RegistrationValidator;
import com.social.server.validator.UserDetailValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl extends CommonServiceImpl<User, Long, UserRepository> implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final PhotoSaver<User> photoSaver;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           PhotoSaver<User> photoSaver) {
        super(userRepository);
        this.photoSaver = photoSaver;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto findBy(long id) {
        return UserDto.of(getById(id));
    }

    @Override
    @WriteTransactional
    public UserDto registerUser(RegistrationModel registrationModel) {
        RegistrationValidator.validate(registrationModel);
        User user = User.builder()
                .email(registrationModel.getEmail())
                .password(passwordEncoder.encode(registrationModel.getPassword()))
                .name(formatName(registrationModel.getName()))
                .surname(formatName(registrationModel.getSurname()))
                .sex(registrationModel.getSex())
                .create();
        return UserDto.of(repository.save(user));
    }

    @Override
    public boolean isEmailExist(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    @WriteTransactional
    public UserDto updateProfile(UserDetailsModel userDetailsModel) {
        UserDetailValidator.validate(userDetailsModel);
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

        return UserDto.of(user);
    }

    @Override
    public List<UserDto> search(long rootUserId, String userName) {
        validateEmptyEntityId(rootUserId);
        log.debug("Search users by userName={}", userName);
        if (StringUtils.isBlank(userName)) {
            return Collections.emptyList();
        }

        String[] nameAndSurname = userName.split(" ");
        if (nameAndSurname.length > 1) {
            log.debug("Search users by name={} and surname={}", formatName(nameAndSurname[0]), formatName(nameAndSurname[1]));
            return UserDto.of(repository.searchByFullName(rootUserId, formatName(nameAndSurname[0]), formatName(nameAndSurname[1])));
        }
        log.debug("Search users by name={}", formatName(nameAndSurname[0]));
        return UserDto.of(repository.searchByFullName(rootUserId, formatName(nameAndSurname[0]), ""));
    }

    @Override
    @ReadTransactional
    public UserDto findBy(String email, String password) {
        User user = repository.findByEmail(email);
        return user != null && passwordEncoder.matches(password, user.getPassword()) ? UserDto.of(user) : null;
    }

    @Override
    @WriteTransactional
    public String savePhoto(long userId, MultipartFile file, boolean isMini) {
        validateEmptyEntityId(userId);
        return photoSaver.savePhoto(getById(userId), file, isMini);
    }

    @Override
    @WriteTransactional
    public void changePassword(RestorePasswordModel model) {
        PasswordValidator.validate(model);
        User user = getById(model.getId());
        user.setPassword(passwordEncoder.encode(model.getPassword()));
    }

    private String formatName(String word) {
        if (StringUtils.isBlank(word)) {
            return word;
        }
        word = word.toLowerCase();
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }
}
