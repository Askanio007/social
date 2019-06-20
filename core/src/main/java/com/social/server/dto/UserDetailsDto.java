package com.social.server.dto;

import com.social.server.entity.Sex;
import com.social.server.entity.UserDetails;
import com.social.server.util.ImageUtil;
import lombok.Data;

import java.time.LocalDateTime;

import static com.social.server.util.DateFormatterUtil.viewFormat;
import static com.social.server.util.DateFormatterUtil.viewInputFormat;

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
    private String image64code;

    public static UserDetailsDto of(UserDetails userDetails) {
        UserDetailsDto dto = new UserDetailsDto();
        if (userDetails != null) {
            dto.setAbout(userDetails.getAbout());
            if (userDetails.getBirthday() != null) {
                dto.setBirthdayView(userDetails.getBirthday().format(viewFormat));
                dto.setBirthdayInputView(userDetails.getBirthday().format(viewInputFormat));
                dto.setBirthday(userDetails.getBirthday());
            }
            dto.setCity(userDetails.getCity());
            dto.setCountry(userDetails.getCountry());
            dto.setPhone(userDetails.getPhone());
            dto.setSex(userDetails.getSex());
            dto.setImage64code(ImageUtil.convertImageTo64encode(userDetails.getImage()));
        }
        return dto;
    }
}
