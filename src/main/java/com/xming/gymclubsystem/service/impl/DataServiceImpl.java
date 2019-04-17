package com.xming.gymclubsystem.service.impl;

import com.xming.gymclubsystem.domain.Gym;
import com.xming.gymclubsystem.domain.Role;
import com.xming.gymclubsystem.domain.Trainer;
import com.xming.gymclubsystem.domain.UmUser;
import com.xming.gymclubsystem.dto.UserSignUpRequest;
import com.xming.gymclubsystem.repository.GymRepository;
import com.xming.gymclubsystem.repository.RoleRepository;
import com.xming.gymclubsystem.repository.TrainerRepository;
import com.xming.gymclubsystem.repository.UserRepository;
import com.xming.gymclubsystem.service.DataService;
import com.xming.gymclubsystem.service.JwtUserDetailsService;
import com.xming.gymclubsystem.service.UserService;
import com.xming.gymclubsystem.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
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


    @Override
    public Gym getGym(String gymname) {
        return gymRepository.findByGymName(gymname);
    }

    @Override
    public Trainer getTrainer(String tname) {
        return trainerRepository.getByName(tname);
    }

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
    public void updateTrainerSalary(String tname, String Salary) {
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


}
