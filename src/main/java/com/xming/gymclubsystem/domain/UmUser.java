package com.xming.gymclubsystem.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Xiaoming.
 * Created on 2019/03/29 15:10.
 */

@Entity(name = "user")
@Data
public class UmUser implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, length = 128)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    private String nickname;

    @Column
    private Date lastPasswordReset;

    private boolean enable;


    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "uid", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "rid", referencedColumnName = "id")})
    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private List<Role> roles = new LinkedList<>();

    public UmUser() {
    }

    public UmUser(Long id, String password) {
        this.id = id;
        this.password = password;
    }

    public UmUser(Long id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    @Override
    public String toString() {
        return "UmUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
