package com.social.server.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Сущность диалогов пользователей
 */
@Table(name = "dialog")
@Entity
@Getter
@Setter
public class Dialog {
    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(unique = true, nullable = false)
    private Long id;

    /**
     * Пользователи, участвующие в диалоге {@link User}
     */
    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_dialog",
            joinColumns = { @JoinColumn(name = "dialog_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") })
    private Set<User> users = new HashSet<>();

    /**
     * Последнее сообщение в диалоге
     */
    @Column(name = "last_message")
    private String lastMessage;

    /**
     * Дата последнего сообщения в диалоге
     */
    @Column(name = "date_last_message")
    private LocalDateTime dateLastMessage;

    /**
     * Список сообщений в диалоге {@link PrivateMessage}
     */
    @OneToMany(mappedBy = "dialog", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PrivateMessage> messages;

}
