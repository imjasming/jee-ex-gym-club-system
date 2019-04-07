package com.xming.gymclubsystem.repository;

import com.xming.gymclubsystem.domain.UmUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Xiaoming.
 * Created on 2018/11/22 11:30.
 * Description :This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
 * CRUD refers Create, Read, Update, Delete
 */
public interface UserRepository extends JpaRepository<UmUser, Long> {

    UmUser findByUsername(String username);

    List<UmUser> findAllByUsername(String username);

    UmUser findByEmail(String email);

    List<UmUser> findAllByEmail(String email);

    UmUser findUserByUsernameAndPassword(String username, String password);

    UmUser findUserByIdAndAndPassword(Long id, String password);
}
