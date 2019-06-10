package com.social.server.dto;

import com.social.server.entity.UserDetails;
import lombok.Data;

@Data
public class UserDetailsDto {
    private Integer age;
    private String country;
    private String city;
    private String phone;
    private String about;

    public static UserDetailsDto of(UserDetails userDetails) {
        UserDetailsDto dto = new UserDetailsDto();
        if (userDetails != null) {
            dto.setAbout(userDetails.getAbout());
            dto.setAge(userDetails.getAge());
            dto.setCity(userDetails.getCity());
            dto.setCountry(userDetails.getCountry());
            dto.setPhone(userDetails.getPhone());
        }
        return dto;
    }
}
