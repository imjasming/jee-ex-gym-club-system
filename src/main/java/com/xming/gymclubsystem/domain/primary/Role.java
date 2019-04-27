package com.xming.gymclubsystem.domain.primary;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Xiaoming.
 * Created on 2019/03/12 23:18.
 * Description :
 */
@Data
@Entity
public class Role implements Serializable {
    private static final long serialVersionUID = 4948892981871034574L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq")
    @SequenceGenerator(name = "role_seq", sequenceName = "role_seq", allocationSize = 1)
    private int id;

    @Column(name = "name", nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private RoleName name;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "roles")
    @JsonIgnoreProperties(ignoreUnknown = true, value = {"roles"})
    private List<UmUser> users = new LinkedList<>();

    public Role(RoleName roleName) {
        this.name = roleName;
    }

    public enum RoleName {
        ROLE_USER, ROLE_ADMIN
    }

    @Override
    public String toString() {
        return this.name.toString();
    }


}
