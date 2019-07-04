package com.social.server.http.model;

import com.social.server.entity.Sex;
import com.social.server.validator.birthday.BirthdayValid;
import com.social.server.validator.phone.PhoneValid;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static com.social.server.http.ErrorCode.*;

@Data
public class UserDetailsModel {

    private long id;

    @Length(min = 3, message = NAME_INCORRECT)
    @NotNull(message = NAME_EMPTY)
    private String name;

    @Length(min = 3, message = SURNAME_INCORRECT)
    @NotNull(message = SURNAME_EMPTY)
    private String surname;

    @NotNull(message = SEX_EMPTY)
    private Sex sex;

    @Length(min = 3, message = DETAILS_CITY_INCORRECT)
    private String city;

    @Length(min = 3, message = DETAILS_COUNTRY_INCORRECT)
    private String country;

    private String about;

    @BirthdayValid(message = DETAILS_BIRTHDAY_INCORRECT)
    private LocalDateTime birthday;

    @PhoneValid(message = DETAILS_PHONE_INCORRECT)
    private String phone;
}
