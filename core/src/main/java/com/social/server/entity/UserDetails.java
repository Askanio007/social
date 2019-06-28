package com.social.server.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Сущность данных пользователя
 */
@Table(name = "user_details_social")
@Entity
@Data
@EqualsAndHashCode(exclude = "user")
@ToString(exclude = "user")
public class UserDetails implements Serializable {
    /**
     * Идентификатор
     */
    @Id
    @GenericGenerator(
            name = "generator",
            strategy = "foreign",
            parameters = @Parameter(name = "property", value = "user"))
    @GeneratedValue(generator = "generator")
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    /**
     * День рождения
     */
    @Column(name = "birthday")
    private LocalDateTime birthday;

    /**
     * Страна
     */
    @Column(name = "country")
    private String country;

    /**
     * Город
     */
    @Column(name = "city")
    private String city;

    /**
     * Телефон
     */
    @Column(name = "phone")
    private String phone;

    /**
     * Дополнительная информация
     */
    @Column(name = "about")
    private String about;

    /**
     * Пол
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "sex", length = 10)
    private Sex sex;

    /**
     * Фотография
     */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image image;

    /**
     * Пользователь
     */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "details")
    private User user;

}
