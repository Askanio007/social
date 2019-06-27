package com.social.server.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Table(name = "public_message")
@Entity
@Data
public class PublicMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(unique = true, nullable = false)
    private Long id;

    @NotNull
    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @NotNull
    @Length(max = 2000)
    @Column(name = "message", nullable = false, length = 2000)
    private String message;

    @NotNull
    @JoinColumn(name = "sender_id", nullable = false)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User sender;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "recipient_type", nullable = false)
    private PublicMessageRecipientType recipientType;

    @NotNull
    @Column(name = "recipient_id", nullable = false)
    private long recipientId;

}
