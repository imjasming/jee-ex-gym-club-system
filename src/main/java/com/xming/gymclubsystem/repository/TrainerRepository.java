package com.xming.gymclubsystem.repository;

import com.xming.gymclubsystem.domain.Role;
import com.xming.gymclubsystem.domain.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Xiaoming.
 * Created on 2018/11/22 11:30.
 * Description :This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
 * CRUD refers Create, Read, Update, Delete
 */
public interface TrainerRepository extends JpaRepository<Trainer, Long> , JpaSpecificationExecutor<Trainer> {


}