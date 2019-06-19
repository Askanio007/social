package com.social.server.dto;

import com.social.server.entity.Group;
import com.social.server.entity.User;
import lombok.Data;
import util.ImageUtil;

@Data
public class PhotoAndNameDto {
    private long id;
    private String fullname;
    private String image64code;

    public static PhotoAndNameDto of(User user) {
        if (user != null) {
            PhotoAndNameDto photoAndNameDto = new PhotoAndNameDto();
            photoAndNameDto.setId(user.getId());
            photoAndNameDto.setFullname(user.getFullName());
            photoAndNameDto.setImage64code(ImageUtil.convertImageTo64encode(user.getDetails().getImage()));
            return photoAndNameDto;
        }
        return null;
    }

    public static PhotoAndNameDto of(Group group) {
        if (group != null) {
            PhotoAndNameDto photoAndNameDto = new PhotoAndNameDto();
            photoAndNameDto.setId(group.getId());
            photoAndNameDto.setFullname(group.getName());
            photoAndNameDto.setImage64code(ImageUtil.convertImageTo64encode(group.getImage()));
            return photoAndNameDto;
        }
        return null;
    }
}
