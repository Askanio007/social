package com.social.server.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Table(name = "friendship_request")
@Entity
@Data
public class FriendshipRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(unique = true, nullable = false)
    private Long id;

    @NotNull
    @JoinColumn(name = "user_id_to", nullable = false)
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private User requestTo;

    @NotNull
    @JoinColumn(name = "user_id_from", nullable = false)
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private User requestFrom;

    @NotNull
    @Column(name = "accept", nullable = false, columnDefinition = "boolean default false")
    private boolean accept;
}
