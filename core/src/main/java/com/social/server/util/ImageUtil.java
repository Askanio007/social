package com.social.server.util;

import com.social.server.entity.Image;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;

import static com.social.server.util.FileUtil.getFileByteBy;

public class ImageUtil {
    private final static String PHOTO_FILE_PATH = "./photo/big/";
    private final static String MINI_PHOTO_FILE_PATH = "./photo/mini/";
    private final static String DEFAULT_PIC = encodeFileToBase64("camera_200.png");

    public static String convertImageTo64encode(Image image) {
        return image == null ? DEFAULT_PIC : encodeFileToBase64(image.getPath());
    }

    private static String encodeFileToBase64(String path) {
        return Base64.encodeBase64String(getFileByteBy(Paths.get(path)));
    }

    public static Image saveImage(MultipartFile file, long id, boolean isMini) {
        String fileName = RandomStringUtils.random(10, true, true) + id;
        fileName = isMini ? MINI_PHOTO_FILE_PATH + fileName + "_mini" : PHOTO_FILE_PATH + fileName;
        Path path = FileUtil.writeFile(file, fileName);
        return path != null ? Image.of(file.getOriginalFilename(), path) : null;
    }
}
