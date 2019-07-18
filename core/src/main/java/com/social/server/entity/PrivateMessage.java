package com.social.server.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Сущность приватных сообщений
 */
@Table(name = "private_message")
@Entity
@Data
public class PrivateMessage {
    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(unique = true, nullable = false)
    private long id;

    /**
     * Время создания
     */
    @NotNull
    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate = LocalDateTime.now();

    /**
     * Текст сообщения
     */
    @NotNull
    @Length(max = 500)
    @Column(name = "message", nullable = false, length = 500)
    private String message;

    /**
     * Отправитель
     */
    @NotNull
    @JoinColumn(name = "sender_id", nullable = false)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User sender;

    /**
     * Прочитано/Не прочитано
     */
    @NotNull
    @Column(name = "read", nullable = false)
    private boolean read = false;

    /**
     * Диалог, которму принадлежит сообщение {@link Dialog}
     */
    @NotNull
    @JoinColumn(name = "dialog_id", nullable = false)
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Dialog dialog;
}
