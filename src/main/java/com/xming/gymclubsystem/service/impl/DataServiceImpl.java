package com.xming.gymclubsystem.service.impl;

import com.xming.gymclubsystem.domain.primary.Gym;
import com.xming.gymclubsystem.domain.primary.Role;
import com.xming.gymclubsystem.domain.primary.Trainer;
import com.xming.gymclubsystem.domain.primary.UmUser;
import com.xming.gymclubsystem.domain.secondary.Equipment;
import com.xming.gymclubsystem.repository.primary.GymRepository;
import com.xming.gymclubsystem.repository.primary.RoleRepository;
import com.xming.gymclubsystem.repository.primary.TrainerRepository;
import com.xming.gymclubsystem.repository.primary.UserRepository;
import com.xming.gymclubsystem.repository.secondary.EquipmentRepository;
import com.xming.gymclubsystem.service.DataService;
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

    @Autowired
    private EquipmentRepository equipmentRepository;



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

    @Override
    public List<Trainer> getUserTrainers(String uname) {
        List<Trainer> trainers = userRepository.findByUsername(uname).getTrainers();
        return trainers;
    }

    @Override
    public List<UmUser> getTrainerUsers(String tname) {
        List<UmUser> umUsers = trainerRepository.getByName(tname).getUsers();
        return umUsers;
    }

    @Transactional(value = "transactionManagerPrimary")
    @Override
    public void updateGymlocation(String gymname, String location) {
        gymRepository.updateGymLocation(gymname, location);
    }

    @Transactional(value = "transactionManagerPrimary")
    @Override
    public void updateTrainerPosition(String tname, String position) {

        trainerRepository.updateTrainerPosition(tname, position);
    }

    @Transactional(value = "transactionManagerPrimary")
    @Override
    public void updateTrainerSalary(String tname, Double Salary) {
        trainerRepository.updateTrainerSalary(tname, Salary);
    }

    @Transactional(value = "transactionManagerPrimary")
    @Override
    public void updateTrainerTelephone(String tname, String telephone) {
        trainerRepository.updateTrainerTelephone(tname, telephone);
    }

    @Transactional(value = "transactionManagerPrimary")
    @Override
    public void updateTrainerEmail(String tname, String Email) {
        trainerRepository.updateTrainerEmail(tname, Email);
    }

    @Transactional(value = "transactionManagerPrimary")
    @Override
    public void updateUmUserEmail(String uname, String Email) {
        userRepository.updateUmUserEmail(uname, Email);
    }

    @Transactional(value = "transactionManagerPrimary")
    @Override
    public void updateUmUserGym(String uname, Gym gym) {
        userRepository.updateUmUserGym(uname, gym);
    }

    @Transactional(value = "transactionManagerPrimary")
    @Override
    public void updateGymIntro(String gymname, String intro) {
        gymRepository.updateGymIntro(gymname, intro);
    }

    @Transactional(value = "transactionManagerPrimary")
    @Override
    public void updateTrainerIntro(String tname, String intro) {
        trainerRepository.updateTrainerIntro(tname, intro);
    }

    @Transactional(value = "transactionManagerPrimary")
    @Override
    public void updateUserIntro(String uname, String intro) {
        userRepository.updateUmUserIntro(uname, intro);
    }

    @Transactional(value = "transactionManagerPrimary")
    @Override
    public void addUserTrainer(String uname, Trainer trainer) {
        trainerRepository.save(trainer);
        UmUser umUser =null;
        umUser = userRepository.findByUsername(uname);
        umUser.getTrainers().add(trainer);
        userRepository.save(umUser);
    }

    @Override
    public void addUserTrainerByID(String uname, Integer id) {
        UmUser umUser =null;
        umUser = userRepository.findByUsername(uname);
        umUser.getTrainers().add(trainerRepository.findById(id).get());
        userRepository.save(umUser);
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

    @Override
    public Role addRole(Role role) {
        return roleRepository.save(role);
    }

    @Transactional(value = "transactionManagerPrimary")
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
    @Cacheable(value = "gymPages", key = "#pageNo")
    public Page<Gym> pagingGyms(int pageNo, int pageSize) {

        Sort sort = new Sort(Sort.Order.asc("id"));
        PageRequest pageable = new PageRequest(pageNo,pageSize,sort);
        Page<Gym> page = gymRepository.findAll(pageable);

        return page;
    }


    @Override
    @Cacheable(value = "trainerPages", key = "#pageNo")
    public Page<Trainer> pagingTrains(int pageNo, int pageSize) {
        Sort sort = new Sort(Sort.Order.asc("id"));
        PageRequest pageable = new PageRequest(pageNo,pageSize,sort);
        Page<Trainer> page = trainerRepository.findAll(pageable);

        return page;
    }

    @Override
    public void addUserRole(String uname, Role.RoleName rname) {
        UmUser umUser = null;
        umUser = userRepository.findByUsername(uname);
        umUser.getRoles().add(roleRepository.findByName(rname));
        userRepository.save(umUser);
    }

    @Override
    public Equipment addEquipment(Equipment equipment) {
        return  equipmentRepository.save(equipment);
    }

    @Override
    public void delEquipment(String eName) {
        Equipment equipment = null;
        equipment = equipmentRepository.findByEName(eName);
        equipmentRepository.delete(equipment);
    }
}
