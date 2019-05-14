package com.xming.gymclubsystem.domain.primary;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private Integer id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @JsonIgnore
    @Column(length = 128)
    private String password;

    @Column(unique = true)
    private String email;

    private String nickname;

    private String githubId;

    @Column
    private Date lastPasswordReset;

    @JsonIgnore
    private boolean enable;

    private String intro;

    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "uid", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "rid", referencedColumnName = "id")})
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JsonIgnoreProperties(ignoreUnknown = true, value = {"users"})
    private List<Role> roles = new LinkedList<>();

    @JoinColumn(name="GYM_ID")
    @ManyToOne(cascade = {CascadeType.MERGE})
    private Gym gym;

    @JoinTable(name = "user_trainer_relation", joinColumns = {@JoinColumn(name = "uid", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "tid", referencedColumnName = "id")})
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // 巨TMD坑
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @JsonIgnoreProperties(ignoreUnknown = true, value = {"users"})
    private List<Trainer> trainers = new LinkedList<>();

    public UmUser() {
    }

    public UmUser(Integer id, String password) {
        this.id = id;
        this.password = password;
    }

    public UmUser(Integer id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public void setRoles(List<Role> roles) {
        roles.forEach(item -> item.getUsers().add(this));
        this.roles.addAll(roles);
    }
}
