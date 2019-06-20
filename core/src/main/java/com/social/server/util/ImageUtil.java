package com.social.server.util;

import com.social.server.entity.Image;
import org.apache.tomcat.util.codec.binary.Base64;

import java.nio.file.Paths;

import static com.social.server.util.FileUtil.getFileByteBy;

public class ImageUtil {

    private final static String defaultPicPath64code = encodeFileToBase64("camera_200.png");

    public static String convertImageTo64encode(Image image) {
        return image == null ? defaultPicPath64code : encodeFileToBase64(image.getPath());
    }

    private static String encodeFileToBase64(String path) {
        return Base64.encodeBase64String(getFileByteBy(Paths.get(path)));
    }
}
