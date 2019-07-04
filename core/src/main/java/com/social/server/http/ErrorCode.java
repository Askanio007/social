package com.social.server.http;

public class ErrorCode {

    public final static String LOGIN_CREDENTIALS_ERROR = "login.credentials.error";

    public final static String EMAIL_IS_USED = "registration.email.error.already.used";
    public final static String EMAIL_INCORRECT = "registration.email.error.incorrect";
    public final static String EMAIL_EMPTY = "registration.email.error.empty";
    public final static String PASSWORD_INCORRECT = "registration.password.error.incorrect";
    public final static String PASSWORD_EMPTY = "registration.password.error.empty";
    public final static String NAME_INCORRECT = "registration.name.error.incorrect";
    public final static String NAME_EMPTY = "registration.name.error.empty";
    public final static String SURNAME_INCORRECT = "registration.surname.error.incorrect";
    public final static String SURNAME_EMPTY = "registration.surname.error.empty";
    public final static String SEX_EMPTY = "registration.sex.error.empty";

    public final static String GROUP_NAME_EMPTY = "groups.name.error.empty";
    public final static String GROUP_NAME_INCORRECT = "groups.name.error.incorrect";
    public final static String GROUP_DESCRIPTION_EMPTY = "groups.description.error.empty";
    public final static String GROUP_DESCRIPTION_INCORRECT = "groups.description.error.incorrect";

    public final static String DETAILS_CITY_INCORRECT="profile.city.error.incorrect";
    public final static String DETAILS_COUNTRY_INCORRECT="profile.country.error.incorrect";
    public final static String DETAILS_PHONE_INCORRECT="profile.phone.error.incorrect";
    public final static String DETAILS_BIRTHDAY_INCORRECT="profile.birthday.error.incorrect";

    public final static String TOKEN_NOT_FOUND = "login.token.empty";
    public final static String USER_NOT_ACTIVE = "login.user.not.active";

    public final static String ID_IS_EMPTY = "common.error.id.empty";
    public final static String RECIPIENT_TYPE_IS_EMPTY = "public.message.error.type.empty";
    public final static String MESSAGE_IS_EMPTY = "public.message.error.message.empty";

}
