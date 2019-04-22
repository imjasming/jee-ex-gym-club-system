package com.xming.gymclubsystem.domain;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Xiaoming.
 * Created on 2019/03/29 15:10.
 */

@Entity
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

    private String intro;


    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "uid", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "rid", referencedColumnName = "id")})
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Role> roles = new LinkedList<>();

    @JoinColumn(name="GYM_ID")
    @ManyToOne(cascade = {CascadeType.MERGE})
    private Gym gym;

    @JoinTable(name = "user_trainer_relation", joinColumns = {@JoinColumn(name = "uid", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "tid", referencedColumnName = "id")})
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Trainer> trainers = new LinkedList<>();

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
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", lastPasswordReset=" + lastPasswordReset +
                ", enable=" + enable +
                ", intro='" + intro + '\'' +
                ", roles=" + roles +
                ", gym=" + gym +
                ", trainers=" + trainers +
                '}';
    }
}
