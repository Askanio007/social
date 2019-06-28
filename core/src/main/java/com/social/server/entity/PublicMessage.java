package com.social.server.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Сущность публичных сообщений
 */
@Table(name = "public_message")
@Entity
@Data
public class PublicMessage {
    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(unique = true, nullable = false)
    private Long id;

    /**
     * Дата создания
     */
    @NotNull
    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate = LocalDateTime.now();

    /**
     * Текст сообщения
     */
    @NotNull
    @Length(max = 2000)
    @Column(name = "message", nullable = false, length = 2000)
    private String message;

    /**
     * Отправитель
     */
    @NotNull
    @JoinColumn(name = "sender_id", nullable = false)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User sender;

    /**
     * Тип получателя {@link PublicMessageRecipientType}
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "recipient_type", nullable = false)
    private PublicMessageRecipientType recipientType;

    /**
     * Идентификатор получателя
     */
    @NotNull
    @Column(name = "recipient_id", nullable = false)
    private long recipientId;

}
