package com.social.server.dto;

import com.social.server.entity.Sex;
import com.social.server.entity.UserDetails;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

import static com.social.server.util.DateFormatterUtil.inputFormat;
import static com.social.server.util.DateFormatterUtil.withoutTimeFormat;

@Slf4j
@Data
public class UserDetailsDto {
    private LocalDateTime birthday;
    private String birthdayView;
    private String birthdayInputView;
    private String country;
    private String city;
    private String phone;
    private String about;
    private Sex sex;
    private long imageId;
    private long miniImageId;

    public static UserDetailsDto of(UserDetails userDetails) {
        if (userDetails == null) {
            log.warn("Entity UserDetails is null");
            return null;
        }

        UserDetailsDto dto = new UserDetailsDto();
        dto.setAbout(userDetails.getAbout());
        dto.setBirthday(userDetails.getBirthday());
        dto.setBirthdayView(withoutTimeFormat(userDetails.getBirthday()));
        dto.setBirthdayInputView(inputFormat(userDetails.getBirthday()));
        dto.setCity(userDetails.getCity());
        dto.setCountry(userDetails.getCountry());
        dto.setPhone(userDetails.getPhone());
        dto.setSex(userDetails.getSex());
        if (userDetails.getImage() != null) {
            dto.setImageId(userDetails.getImage().getId());
        }
        if (userDetails.getMiniImage() != null) {
            dto.setMiniImageId(userDetails.getMiniImage().getId());
        }
        return dto;
    }
}
