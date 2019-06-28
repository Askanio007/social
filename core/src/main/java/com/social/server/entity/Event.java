package com.social.server.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Сущность событий пользователей
 */
@Entity
@Table(name = "event")
@Data
public class Event {
    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(unique = true, nullable = false)
    private Long id;

    /**
     * Пользователь, с которым связано событие
     */
    @NotNull
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Время в которое произошло событие
     */
    @NotNull
    @Column(name = "create_date", nullable = false)
    private LocalDateTime date = LocalDateTime.now();

    /**
     * Тип события {@link EventType}
     */
    @NotNull
    @Column(name = "type", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private EventType type;

    /**
     * Имя/название объекта, с которым связано событие
     */
    @Length(max = 100)
    @Column(name = "target_action_name", length = 100)
    private String targetActionName;

    /**
     * идентификаторо объекта, с которым связано событие
     */
    @NotNull
    @Column(name = "target_action_id", nullable = false)
    private Long targetActionId;

}
