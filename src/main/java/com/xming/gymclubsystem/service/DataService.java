package com.xming.gymclubsystem.service;

import com.xming.gymclubsystem.domain.primary.Gym;
import com.xming.gymclubsystem.domain.primary.Role;
import com.xming.gymclubsystem.domain.primary.Trainer;
import com.xming.gymclubsystem.domain.primary.UmUser;
import com.xming.gymclubsystem.domain.secondary.Equipment;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author Xiaoming.
 * Created on 2019/03/29 18:21.
 */
public interface DataService {
    //get
    Gym getGym(String gymname);
    Trainer getTrainer(String tname);
    UmUser getUser(String uname);
    List<Trainer> getUserTrainers(String uname);
    List<UmUser> getTrainerUsers(String tname);

    //update
    void updateGymlocation(String gymname,String location);
    void updateTrainerPosition(String tname,String position);
    void  updateTrainerSalary(String tname,Double Salary);
    void updateTrainerTelephone(String tname,String telephone);
    void updateTrainerEmail(String tname,String Email);
    void updateUmUserEmail(String uname,String Email);

    void updateUmUserGym(String uname,Gym gym);

    void updateGymIntro(String gymname,String intro);
    void updateTrainerIntro(String tname,String intro);
    void updateUserIntro(String uname,String intro);

    //add
    Gym addGym(Gym gym);
    Trainer addTrainer(Trainer trainer);
    UmUser addUser(UmUser umUser);
    Role addRole(Role role);
    void addUserTrainer(String uname, Trainer trainer);

    void addUserTrainerByID(String uname, Integer id);

    //delete
    void deleteGym(String gymname);
    void deleteTrainer(String tname);
    void deleteUser(String uname);

    //PaginandSort
    Page<Gym> pagingGyms(int pageNo, int pageSize);
    Page<Trainer> pagingTrains(int pageNo,int pageSize);

    void addUserRole(String uname, Role.RoleName rname);


    //for equipment
    Equipment addEquipment(Equipment equipment);
    void delEquipment(String eName);
}
