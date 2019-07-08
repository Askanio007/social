package com.social.server.controller;

import com.social.server.entity.Sex;
import com.social.server.http.ErrorCode;
import com.social.server.http.Response;
import com.social.server.http.model.UserDetailsModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@RunWith(SpringRunner.class)
@WebMvcTest(ProfileController.class)
public class ProfileControllerTest extends CommonControllerTest {

    private UserDetailsModel getUserDetailsModel() {
        UserDetailsModel detailsModel = new UserDetailsModel();
        detailsModel.setId(ID);
        detailsModel.setName("test");
        detailsModel.setSurname("test");
        detailsModel.setSex(Sex.MALE);
        detailsModel.setPhone("+79531851461");
        detailsModel.setCity("test");
        detailsModel.setAbout("test");
        detailsModel.setCountry("test");
        detailsModel.setBirthday(LocalDateTime.of(2018, 4, 12, 18, 40, 0));
        detailsModel.setBirthday(new Timestamp(new Date().getTime()).toLocalDateTime());
        return detailsModel;
    }

    @Test
    public void incorrectBirthdaySaveProfile() throws Exception {
        UserDetailsModel model = getUserDetailsModel();
        model.setBirthday(null);
        checkPostRequest("/api/v1/" + ID + "/profile", model, Response.error(ErrorCode.DETAILS_BIRTHDAY_INCORRECT));
    }

    //@Test
    public void successSaveProfile() throws Exception {
        UserDetailsModel model = getUserDetailsModel();
        checkPostRequest("/api/v1/" + ID + "/profile", model, Response.error(ErrorCode.DETAILS_BIRTHDAY_INCORRECT));
    }
}
