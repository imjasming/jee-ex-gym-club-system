package com.xming.gymclubsystem.service.impl;

import com.xming.gymclubsystem.domain.Gym;
import com.xming.gymclubsystem.domain.Trainer;
import com.xming.gymclubsystem.domain.UmUser;
import com.xming.gymclubsystem.repository.GymRepository;
import com.xming.gymclubsystem.repository.RoleRepository;
import com.xming.gymclubsystem.repository.TrainerRepository;
import com.xming.gymclubsystem.repository.UserRepository;
import com.xming.gymclubsystem.service.DataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

/**
 * @author Xiaoming.
 * Created on 2019/03/29 20:46.
 */
@Slf4j
@Service("dataService")
public class DataServiceImpl implements DataService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private GymRepository gymRepository;

    @Autowired
    private TrainerRepository trainerRepository;



    @Cacheable(value = "gym",key = "#gymname")
    @Override
    public Gym getGym(String gymname) {
        return gymRepository.findByGymName(gymname);
    }

    @Cacheable(value = "trainer",key = "#tname")
    @Override
    public Trainer getTrainer(String tname) {
        return trainerRepository.getByName(tname);
    }

    @Cacheable(value = "umuser",key = "#uname")
    @Override
    public UmUser getUser(String uname) {
        return userRepository.findByUsername(uname);
    }

    @Transactional
    @Override
    public void updateGymlocation(String gymname, String location) {
        gymRepository.updateGymLocation(gymname, location);
    }

    @Transactional
    @Override
    public void updateTrainerPosition(String tname, String position) {

        trainerRepository.updateTrainerPosition(tname, position);
    }

    @Transactional
    @Override
    public void updateTrainerSalary(String tname, Double Salary) {
        trainerRepository.updateTrainerSalary(tname, Salary);
    }

    @Transactional
    @Override
    public void updateTrainerTelephone(String tname, String telephone) {
        trainerRepository.updateTrainerTelephone(tname, telephone);
    }

    @Transactional
    @Override
    public void updateTrainerEmail(String tname, String Email) {
        trainerRepository.updateTrainerEmail(tname, Email);
    }

    @Transactional
    @Override
    public void updateUmUserEmail(String uname, String Email) {
        userRepository.updateUmUserEmail(uname, Email);
    }

    @Transactional
    @Override
    public void updateUmUserGym(String uname, Gym gym) {
        userRepository.updateUmUserGym(uname, gym);
    }

    @Transactional
    @Override
    public void updateGymIntro(String gymname, String intro) {
        gymRepository.updateGymIntro(gymname,intro);
    }

    @Transactional
    @Override
    public void updateTrainerIntro(String tname, String intro) {
        trainerRepository.updateTrainerIntro(tname,intro);
    }

    @Transactional
    @Override
    public void updateUserIntro(String uname, String intro) {
        userRepository.updateUmUserIntro(uname,intro);
    }


    @Override
    public Gym addGym(Gym gym) {
        return gymRepository.save(gym);
    }

    @Override
    public Trainer addTrainer(Trainer trainer) {
        return trainerRepository.save(trainer);
    }

    @Override
    public UmUser addUser(UmUser umUser) {
        return userRepository.save(umUser);
    }

    @Transactional
    @Override
    public void deleteGym(String gymname) {
        Gym gym = null;
        gym = gymRepository.findByGymName(gymname);

        //1. 删除user的外键关联
        List<UmUser> users = userRepository.getUmUserGym(gym);

        for (UmUser user: users
             ) {
            userRepository.updateUmUserGymIsNull(user.getUsername());
        }

        //2. 删除trainer的外键关联

        List<Trainer> trainers = trainerRepository.getTrainerGym(gym);

        for (Trainer trainer: trainers
        ) {
            trainerRepository.updateTrainerGymIsNull(trainer.getName());
        }

        gymRepository.delete(gym);

    }

    @Override
    public void deleteTrainer(String tname) {
        Trainer trainer = null;
        trainer = trainerRepository.getByName(tname);
        trainerRepository.delete(trainer);

    }

    @Override
    public void deleteUser(String uname) {
        UmUser umUser = null;
        umUser = userRepository.findByUsername(uname);
        userRepository.delete(umUser);
    }

    @Override
    public Page<Gym> pagingGyms(int pageNo, int pageSize) {

        Sort sort = new Sort(Sort.Order.asc("id"));
        PageRequest pageable = new PageRequest(pageNo,pageSize,sort);
        Page<Gym> page = gymRepository.findAll(pageable);
        //以下为page使用方法

//        System.out.println("总记录数"+page.getTotalElements());
//        System.out.println("当前第几页"+(page.getNumber()+1));
//        System.out.println("总页数："+page.getTotalPages());
//        System.out.println("当前页面的LIST:"+page.getContent());
//        System.out.println("当前页面的记录数"+page.getNumberOfElements());

        return page;
    }


    @Override
    public Page<Trainer> pagingTrains(int pageNo, int pageSize) {
        Sort sort = new Sort(Sort.Order.asc("id"));
        PageRequest pageable = new PageRequest(pageNo,pageSize,sort);
        Page<Trainer> page = trainerRepository.findAll(pageable);

        return page;
    }


    @Transactional
    @Override
    public void changePassword(String uname,String password) {
        userRepository.updateUmUserPassword(uname,password);
    }


}
