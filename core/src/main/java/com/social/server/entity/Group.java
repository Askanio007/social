package com.social.server.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * Сущность группы
 */
@Table(name = "group_social")
@Entity
@Data
public class Group implements ShortModel {
    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(unique = true, nullable = false)
    private Long id;

    /**
     * Название группы
     */
    @NotNull
    @Length(max = 100)
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    /**
     * Описание группы
     */
    @Length(max = 500)
    @Column(name = "description", length = 500)
    private String description;

    /**
     * Администратор группы (Создатель) {@link User}
     */
    @NotNull
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false)
    private User admin;

    /**
     * Участники группы
     */
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_group",
            joinColumns = { @JoinColumn(name = "group_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") })
    private Set<User> users = new HashSet<>();

    /**
     * Фотография группы
     */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image image;

    @Override
    public String getFullName() {
        return this.name;
    }
}
