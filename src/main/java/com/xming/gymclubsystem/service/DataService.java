package com.xming.gymclubsystem.service;

import com.xming.gymclubsystem.domain.Gym;
import com.xming.gymclubsystem.domain.Trainer;
import com.xming.gymclubsystem.domain.UmUser;
import com.xming.gymclubsystem.dto.UserSignUpRequest;
import org.springframework.data.domain.Page;

/**
 * @author Xiaoming.
 * Created on 2019/03/29 18:21.
 */
public interface DataService {
    //get
    Gym getGym(String gymname);
    Trainer getTrainer(String tname);
    UmUser getUser(String uname);

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

    //delete
    void deleteGym(String gymname);
    void deleteTrainer(String tname);
    void deleteUser(String uname);


    //PaginandSort
    Page<Gym> pagingGyms(int pageNo, int pageSize);
    Page<Trainer> pagingTrains(int pageNo,int pageSize);






}
