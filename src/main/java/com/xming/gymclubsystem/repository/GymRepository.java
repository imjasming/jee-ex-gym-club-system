package com.xming.gymclubsystem.repository;

import com.xming.gymclubsystem.domain.Gym;
import com.xming.gymclubsystem.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author Xiaoming.
 * Created on 2018/11/22 11:30.
 * Description :This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
 * CRUD refers Create, Read, Update, Delete
 */
public interface GymRepository extends JpaRepository<Gym, Long> , JpaSpecificationExecutor<Gym> {

    Gym findByGymName(String name);

    @Modifying
    @Query("UPDATE Gym g set g.location = :locations where g.gymName = :gname")
    void updateGymLocation(@Param("gname") String gname,@Param("locations") String locations);

}