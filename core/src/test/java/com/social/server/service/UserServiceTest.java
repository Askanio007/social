package com.social.server.service;

import com.social.server.dao.UserRepository;
import com.social.server.dto.UserDto;
import com.social.server.entity.Sex;
import com.social.server.entity.User;
import com.social.server.entity.UserDetails;
import com.social.server.http.model.RegistrationModel;
import com.social.server.http.model.UserDetailsModel;
import com.social.server.service.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;

public class UserServiceTest {

    private final static long USER_ID = 1;
    private final static String NAME = "john";
    private final static String CHECK_NAME = "John";
    private final static String SURNAME = "sina";
    private final static String CHECK_SURNAME = "Sina";
    private final static String EMAIL = "sss@ddd.com";
    private final static String PASSWORD = "123456";

    private final static String ABOUT = "ABOUT";
    private final static String PHONE = "PHONE";
    private final static String COUNTRY = "COUNTRY";
    private final static String CITY = "CITY";
    private final static LocalDateTime BIRTHDAY = LocalDateTime.now();
    private final static Sex EDIT_SEX = Sex.FEMALE;

    private final static Sex SEX = Sex.MALE;

    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final ImageService imageService = Mockito.mock(ImageService.class);
    private final UserService userService =  new UserServiceImpl(userRepository, passwordEncoder, imageService);

    @Before
    public void setUp() {
        Mockito.when(userRepository.save(any(User.class))).then(invocation -> invocation.getArgument(0));
    }

    @Test
    public void successRegistration() {
        RegistrationModel model = new RegistrationModel();
        model.setEmail(EMAIL);
        model.setName(NAME);
        model.setSurname(SURNAME);
        model.setPassword(PASSWORD);
        model.setSex(SEX);

        UserDto dto = userService.registerUser(model);
        Assert.assertEquals(dto.getEmail(), EMAIL);
        Assert.assertEquals(dto.getName(), CHECK_NAME);
        Assert.assertEquals(dto.getSurname(), CHECK_SURNAME);
        Assert.assertEquals(dto.getDetails().getSex(), SEX);
    }

    @Test
    public void successUpdateDetails() {
        User user = new User();
        UserDetails details = new UserDetails();
        details.setAbout("check");
        details.setBirthday(LocalDateTime.now().plusDays(10));
        details.setCity("check");
        details.setCountry("check");
        details.setPhone("check");
        user.setName(CHECK_NAME);
        user.setSurname(CHECK_SURNAME);
        user.setDetails(details);

        Mockito.when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));

        UserDetailsModel userDetailsModel = new UserDetailsModel();
        userDetailsModel.setAbout(ABOUT);
        userDetailsModel.setCity(CITY);
        userDetailsModel.setCountry(COUNTRY);
        userDetailsModel.setBirthday(BIRTHDAY);
        userDetailsModel.setPhone(PHONE);
        userDetailsModel.setSex(EDIT_SEX);
        userDetailsModel.setName(NAME);
        userDetailsModel.setSurname(SURNAME);
        userDetailsModel.setId(USER_ID);

        UserDto dto = userService.updateProfile(userDetailsModel);
        Assert.assertEquals(dto.getName(), NAME);
        Assert.assertEquals(dto.getSurname(), SURNAME);
        Assert.assertEquals(dto.getDetails().getSex(), EDIT_SEX);
        Assert.assertEquals(dto.getDetails().getAbout(), ABOUT);
        Assert.assertEquals(dto.getDetails().getBirthday(), BIRTHDAY);
        Assert.assertEquals(dto.getDetails().getCity(), CITY);
        Assert.assertEquals(dto.getDetails().getCountry(), COUNTRY);
        Assert.assertEquals(dto.getDetails().getPhone(), PHONE);
    }

    @Test
    public void checkSearchByNameOnly() {
        String name = "test";
        userService.search(USER_ID, name);
        ArgumentCaptor<String> surnameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(userRepository).searchByFullName(eq(USER_ID), nameCaptor.capture(), surnameCaptor.capture());
        Assert.assertEquals(nameCaptor.getValue(), "Test");
        Assert.assertEquals(surnameCaptor.getValue(), "");
    }

    @Test
    public void checkSearchByNameAndSurname() {
        String name = "foo bar";
        userService.search(USER_ID, name);
        ArgumentCaptor<String> surnameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(userRepository).searchByFullName(eq(USER_ID), nameCaptor.capture(), surnameCaptor.capture());
        Assert.assertEquals(nameCaptor.getValue(), "Foo");
        Assert.assertEquals(surnameCaptor.getValue(), "Bar");
    }

    @Test
    public void checkSearchByEmpty() {
        String name = " ";
        List<UserDto> list = userService.search(USER_ID, name);
        Mockito.verify(userRepository, times(0)).searchByFullName(eq(0), any(), any());
        Assert.assertEquals(list.size(), 0);
    }

    @Test
    public void checkSearchByNull() {
        String name = null;
        List<UserDto> list = userService.search(USER_ID, name);
        Mockito.verify(userRepository, times(0)).searchByFullName(eq(0), any(), any());
        Assert.assertEquals(list.size(), 0);
    }
}
