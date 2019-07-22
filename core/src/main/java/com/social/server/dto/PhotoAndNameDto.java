package com.social.server.dto;

import com.social.server.entity.ShortModel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Data
public class PhotoAndNameDto {
    private long id;
    private String fullName;
    private String shortName;
    private long imageId;

    public static <T extends ShortModel> PhotoAndNameDto of(T model) {
        if (model == null) {
            log.warn("Entity ShortModel User is null");
            return null;
        }

        PhotoAndNameDto photoAndNameDto = new PhotoAndNameDto();
        photoAndNameDto.setId(model.getId());
        photoAndNameDto.setFullName(model.getFullName());
        photoAndNameDto.setShortName(cutName(model.getFullName()));
        if (model.getMiniImage() != null) {
            photoAndNameDto.setImageId(model.getMiniImage().getId());
        }
        return photoAndNameDto;
    }

    public static <T extends ShortModel> List<PhotoAndNameDto> of(Set<T> models) {
        return models.stream()
                .map(PhotoAndNameDto::of)
                .collect(Collectors.toList());
    }

    private static String cutName(String name) {
        if (StringUtils.isBlank(name) || name.length() < 13) {
            return name;
        }
        return name.substring(0, 13) + "...";
    }
}
