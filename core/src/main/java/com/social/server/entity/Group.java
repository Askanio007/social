package com.social.server.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Table(name = "group")
@Entity
@Data
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(unique = true, nullable = false)
    private Long id;

    @NotNull
    @Length(max = 100)
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Length(max = 500)
    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "avatar_path", length = 100)
    private String avatarPath;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_group",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "group_id") }
    )
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PublicMessage> messages;


}
