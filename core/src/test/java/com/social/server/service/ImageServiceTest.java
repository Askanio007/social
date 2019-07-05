package com.social.server.service;

import com.social.server.dao.ImageRepository;
import com.social.server.entity.Image;
import com.social.server.service.impl.ImageServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ImageServiceTest {

    private final ImageRepository imageRepository = Mockito.mock(ImageRepository.class);
    private final ImageService imageService = new ImageServiceImpl(imageRepository);

    @Test
    public void successDeleteFile() {
        Path pathTestFile = Paths.get("test.txt");
        try {
            pathTestFile = Files.createFile(pathTestFile);
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
        Assert.assertTrue(Files.exists(pathTestFile));
        Image image = Image.of("test.txt", pathTestFile);
        imageService.deleteImage(image);
        verify(imageRepository, times(1)).delete(eq(image));
        Assert.assertFalse(Files.exists(pathTestFile));
    }

    @Test
    public void notDeleteFile() {
        imageService.deleteImage(null, null);
        verify(imageRepository, times(0)).delete(any());
    }
}
