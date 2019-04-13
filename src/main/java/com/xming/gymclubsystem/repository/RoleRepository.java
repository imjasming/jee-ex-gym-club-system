package com.xming.gymclubsystem.repository;

import com.xming.gymclubsystem.domain.Role;
import com.xming.gymclubsystem.domain.UmUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Xiaoming.
 * Created on 2018/11/22 11:30.
 * Description :This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
 * CRUD refers Create, Read, Update, Delete
 */
public interface RoleRepository extends JpaRepository<Role, Long> , JpaSpecificationExecutor<Role> {


}