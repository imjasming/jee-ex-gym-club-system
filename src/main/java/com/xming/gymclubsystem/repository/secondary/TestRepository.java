package com.xming.gymclubsystem.repository.secondary;

import com.xming.gymclubsystem.domain.primary.Gym;
import com.xming.gymclubsystem.domain.primary.UmUser;
import com.xming.gymclubsystem.domain.secondary.H2test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Xiaoming.
 * Created on 2018/11/22 11:30.
 * Description :This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
 * CRUD refers Create, Read, Update, Delete
 */

public interface TestRepository extends JpaRepository<H2test, Long> , JpaSpecificationExecutor<H2test> {



}