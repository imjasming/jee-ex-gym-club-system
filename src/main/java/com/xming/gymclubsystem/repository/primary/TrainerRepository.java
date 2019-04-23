package com.xming.gymclubsystem.repository.primary;

import com.xming.gymclubsystem.domain.primary.Gym;
import com.xming.gymclubsystem.domain.primary.Trainer;
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
public interface TrainerRepository extends JpaRepository<Trainer, Integer> , JpaSpecificationExecutor<Trainer> {

    Trainer getByName(String name);


    @Modifying
    @Query("UPDATE Trainer t set t.position = :position where t.name = :tname")
    void updateTrainerPosition(@Param("tname") String tname, @Param("position") String position);


    @Modifying
    @Query("UPDATE Trainer t set t.salary = :salary where t.name = :tname")
    void updateTrainerSalary(@Param("tname") String tname, @Param("salary") Double salary);

    @Modifying
    @Query("UPDATE Trainer t set t.telephone = :telephone where t.name = :tname")
    void updateTrainerTelephone(@Param("tname") String tname, @Param("telephone") String telephone);


    @Modifying
    @Query("UPDATE Trainer t set t.email = :email where t.name = :tname")
    void updateTrainerEmail(@Param("tname") String tname, @Param("email") String email);


    @Modifying
    @Query("UPDATE Trainer t set t.intro = :intro where t.name = :tname")
    void updateTrainerIntro(@Param("tname") String tname, @Param("intro") String intro);



    @Modifying
    @Query("UPDATE Trainer t set t.gym = :gym where t.name = :tname")
    void updateTrainerGym(@Param("tname") String tname, @Param("gym") Gym gym);


    @Modifying
    @Query("UPDATE Trainer t set t.gym = null where t.name = :tname")
    void updateTrainerGymIsNull(@Param("tname") String tname);


    @Modifying
    @Query("select t from Trainer t  where t.gym = :gym ")
    List<Trainer> getTrainerGym(@Param("gym") Gym gym);

}