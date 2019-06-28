package com.social.server.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class FileUtil {

    static byte[] getFileByteBy(Path path) {
        return Files.exists(path) ? readAllBytes(path) : null;
    }

    public static Path writeFile(MultipartFile file) {
        try {
            return Files.write(Paths.get(file.getOriginalFilename()), file.getBytes());
        } catch (IOException e) {
            log.error("Write file is failed, file: {}", file.getName(), e);
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
