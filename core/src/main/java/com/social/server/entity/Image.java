package com.social.server.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.nio.file.Path;

/**
 * Сущность фотографий
 */
@Table(name = "image")
@Entity
@Getter
@Setter
public class Image {
    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(unique = true, nullable = false)
    private Long id;

    /**
     * Путь к фотографии на сервере
     */
    @NotNull
    @Column(name = "path", nullable = false)
    private String path;

    /**
     * Имя файла
     */
    @NotNull
    @Column(name = "filename", nullable = false)
    private String filename;

    public static Image of(String filename, Path path) {
        Image image = new Image();
        image.setFilename(filename);
        image.setPath(path.toString());
        return image;
    }
}
