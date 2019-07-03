package com.social.server.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class FileUtil {

    public final static String PHOTO_FILE_PATH = "./photo/big/";
    public final static String MINI_PHOTO_FILE_PATH = "./photo/mini/";

    static byte[] getFileByteBy(Path path) {
        return Files.exists(path) ? readAllBytes(path) : null;
    }

    public static void deleteFile(String path) {
        try {
            Files.deleteIfExists(Paths.get(path));
        } catch (IOException e) {
            log.error("Delete file is failed, path: {}", path, e);
        }
    }

    public static Path writeFile(MultipartFile file, String fileName) {
        try {
            return Files.write(Paths.get(fileName), file.getBytes());
        } catch (IOException e) {
            log.error("Write file is failed, file: {}", fileName, e);
        }
        return null;
    }

    private static byte[] readAllBytes(Path path) {
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            log.error("Read file is failed. File path: {}", path, e);
            return null;
        }
    }
}
