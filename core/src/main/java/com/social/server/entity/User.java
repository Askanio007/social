package com.social.server.entity;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Пользователь
 */
@Table(name = "user_social")
@Entity
@Data
@EqualsAndHashCode(exclude = {"details", "friends", "groups", "messages"})
@ToString(exclude = {"details", "friends", "groups", "messages"})
public class User implements ShortModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(unique = true, nullable = false)
    private Long id;

    @NotNull
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Column(name = "enable", nullable = false)
    private boolean enable;

    @NotNull
    @Length(max = 50)
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @NotNull
    @Length(max = 50)
    @Column(name = "surname", nullable = false, length = 50)
    private String surname;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private UserDetails details;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_friend",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id"))
    private Set<User> friends = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PublicMessage> messages;

    @ManyToMany(mappedBy = "users")
    private Set<Group> groups = new HashSet<>();

    @ManyToMany(mappedBy = "users")
    private Set<Dialog> dialogs = new HashSet<>();

    public String getFullName() {
        return this.name + " " + this.surname;
    }

    @Override
    public Image getImage() {
        return this.details.getImage();
    }
}
