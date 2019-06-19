package com.social.server.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.nio.file.Path;

@Table(name = "image")
@Entity
@Getter
@Setter
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(unique = true, nullable = false)
    private Long id;

    @NotNull
    @Column(name = "path", nullable = false)
    private String path;

    @NotNull
    @Column(name = "filename", nullable = false)
    private String filename;

    @OneToOne(mappedBy = "image", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private UserDetails userDetails;

    @OneToOne(mappedBy = "image", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private Group group;

    public static Image of(String filename, Path path) {
        Image image = new Image();
        image.setFilename(filename);
        image.setPath(path.toString());
        return image;
    }
}
