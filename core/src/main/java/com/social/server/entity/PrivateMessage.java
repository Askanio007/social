package com.social.server.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Table(name = "private_message")
@Entity
@Data
public class PrivateMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(unique = true, nullable = false)
    private Long id;

    @NotNull
    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate = LocalDateTime.now();

    @NotNull
    @Length(max = 500)
    @Column(name = "message", nullable = false, length = 500)
    private String message;

    @NotNull
    @JoinColumn(name = "sender_id", nullable = false)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User sender;

    @NotNull
    @Column(name = "delivered", nullable = false)
    private boolean delivered = false;

    @NotNull
    @Column(name = "read", nullable = false)
    private boolean read = false;

    @NotNull
    @JoinColumn(name = "dialog_id", nullable = false)
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Dialog dialog;
}
