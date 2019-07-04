package com.social.server.entity;

import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * Сущность пользователя
 */
@Table(name = "user_social")
@Entity
@Data
@EqualsAndHashCode(exclude = {"details", "friends", "groups", "dialogs"})
@ToString(exclude = {"details", "friends", "groups", "dialogs"})
public class User implements ShortModel {
    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(unique = true, nullable = false)
    private long id;

    /**
     * Электронная почта
     */
    @NotNull
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /**
     * Пароль
     */
    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * Активен / Неактивен
     */
    @NotNull
    @Column(name = "enable", nullable = false, columnDefinition = "boolean default true")
    private boolean enable = true;

    /**
     * Имя пользователя
     */
    @NotNull
    @Length(max = 50)
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    /**
     * Фамилия пользователя
     */
    @NotNull
    @Length(max = 50)
    @Column(name = "surname", nullable = false, length = 50)
    private String surname;

    /**
     * Дополнитльные данные о пользователе
     */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private UserDetails details = new UserDetails();

    /**
     * Список друзей {@link User}
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_friend",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id"))
    private Set<User> friends = new HashSet<>();

    /**
     * Список групп, в которые вступил пользователь {@link Group}
     */
    @ManyToMany(mappedBy = "users")
    private Set<Group> groups = new HashSet<>();

    /**
     * Список диалогов, в которых участвует пользователь {@link Dialog}
     */
    @ManyToMany(mappedBy = "users")
    private Set<Dialog> dialogs = new HashSet<>();

    /**
     * Получить имя и фамилию одной строкой
     */
    @Override
    public String getFullName() {
        String name = StringUtils.isBlank(this.name) ? "" : this.name;
        String surname = StringUtils.isBlank(this.surname) ? "" : this.surname;
        return (name + " " + surname).trim();
    }

    @Override
    public Image getMiniImage() {
        return this.details.getMiniImage();
    }

    @Override
    public Image getImage() {
        return this.details.getImage();
    }

    @Override
    public void setImage(Image image) {
        this.details.setImage(image);
    }

    @Override
    public void setMiniImage(Image image) {
        this.details.setMiniImage(image);
    }

    public static Builder builder() {
        return new User.Builder();
    }

    public static class Builder {
        private User user;

        private Builder() {
            this.user = new User();
        }

        public Builder email(String email) {
            user.setEmail(email);
            return this;
        }

        public Builder password(String password) {
            user.setPassword(password);
            return this;
        }

        public Builder name(String name) {
            user.setName(name);
            return this;
        }

        public Builder surname(String surname) {
            user.setSurname(surname);
            return this;
        }

        public Builder sex(Sex sex) {
            user.getDetails().setSex(sex);
            return this;
        }

        public User create() {
            return this.user;
        }
    }
}
