package com.social.server.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "password_reset_token")
@Getter
@Setter
public class PasswordResetToken {
    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(unique = true, nullable = false)
    private long id;
    /**
     * Токен
     */
    @NotNull
    @Length(max = 50)
    @Column(name = "token", unique = true, nullable = false, length = 50)
    private String token;
    /**
     * Пользователь
     */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    /**
     * Срок валидности токена
     */
    @Column(name = "expired_date")
    private LocalDateTime expiredDate;
}
