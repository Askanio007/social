package com.social.server.dto;

import com.social.server.entity.ShortModel;
import com.social.server.util.ImageUtil;
import lombok.Data;

@Data
public class PhotoAndNameDto {
    private long id;
    private String fullName;
    private String image64code;

    public static <T extends ShortModel> PhotoAndNameDto of(T model) {
        if (model != null) {
            PhotoAndNameDto photoAndNameDto = new PhotoAndNameDto();
            photoAndNameDto.setId(model.getId());
            photoAndNameDto.setFullName(model.getFullName());
            photoAndNameDto.setImage64code(ImageUtil.convertImageTo64encode(model.getImage()));
            return photoAndNameDto;
        }
        return null;
    }
}
