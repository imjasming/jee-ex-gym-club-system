package com.xming.gymclubsystem.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author Xiaoming.
 * Created on 2019/03/12 23:18.
 * Description :
 */
@Data
@Entity
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq")
    @SequenceGenerator(name = "role_seq", sequenceName = "role_seq", allocationSize = 1)
    private int id;

    @Column(name = "name", nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleName name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<UmUser> users;

    public Role(RoleName roleName) {
        this.name = roleName;
    }

    public enum RoleName {
        ROLE_USER, ROLE_ADMIN
    }
}
