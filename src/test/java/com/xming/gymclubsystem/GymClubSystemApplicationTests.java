package com.xming.gymclubsystem;

import com.xming.gymclubsystem.domain.primary.Gym;
import com.xming.gymclubsystem.domain.primary.Role;
import com.xming.gymclubsystem.domain.primary.Trainer;
import com.xming.gymclubsystem.domain.primary.UmUser;
import com.xming.gymclubsystem.repository.primary.GymRepository;
import com.xming.gymclubsystem.repository.primary.RoleRepository;
import com.xming.gymclubsystem.repository.primary.UserRepository;
import com.xming.gymclubsystem.service.DataService;
import com.xming.gymclubsystem.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

import static com.xming.gymclubsystem.domain.primary.Role.RoleName.ROLE_USER;

/**
 * 使用Spring提供的Junit的运行器
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class GymClubSystemApplicationTests {


	@Autowired
	@Qualifier(value = "entityManagerPrimary")
	private EntityManager em;

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private GymRepository gymRepository;

    @Autowired
    private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;



	@Autowired
	private DataService dataService;


	@Autowired
	private UserService userService;

	@Test
	@Transactional
	@Rollback(false)
	public void contextLoads() {
		Role role1 = new Role(ROLE_USER);
        Role role2 = new Role(ROLE_USER);

		UmUser user1 = new UmUser();
		user1.setNickname("cw");
		user1.setEmail("aaaa@qq.com");
		user1.setUsername("cw");
		user1.setPassword("132465");

		UmUser user2 = new UmUser();
		user2.setNickname("zhz");
		user2.setEmail("sss@qq.com");
		user2.setUsername("chz");
		user2.setPassword("132465673");


		role1.getUsers().add(user1);
		role1.getUsers().add(user2);

		role2.getUsers().add(user1);
		role2.getUsers().add(user2);

		user1.getRoles().add(role1);
		user1.getRoles().add(role2);

		user2.getRoles().add(role1);
		user2.getRoles().add(role2);


        //保存

		roleRepository.save(role1);
		roleRepository.save(role2);
		userRepository.save(user1);
		userRepository.save(user2);





	}


	@Test
	public void testRedis(){
		ValueOperations<String,Object> valueOperations = redisTemplate.opsForValue();

		valueOperations.set("hello","redis");
		System.out.println("UseRedisDap="+valueOperations.get("hello"));


	}


	@Test
	public void testData(){
		Gym gym =new Gym();
		gym.setGymName("aaaa");
		gym.setLocation("BJTU");

		gym = dataService.addGym(gym);

		//dataService.updateUmUserGym("chz",gym);


//		Trainer trainer = new Trainer();
//		trainer.setAge(18);
//		trainer.setEmail("ss@qq.com");
//		trainer.setGym(gym);
//		trainer.setName("cw");
//		trainer.setPosition("PE");
//		trainer.setSalary(20000.3);
//		trainer.setTelephone("18801130810");
//
//		trainer = dataService.addTrainer(trainer);

//		dataService.deleteUser("cw");



	}


	@Test
	public void testDataRedisCache(){

		System.out.println(dataService.getGym("aaaa"));
	}



	@Test
	public void testAddGyms(){
		List<Gym> gyms = new ArrayList<>();
		for (int i = 'a';i<='z';i++){
			Gym gym = new Gym();
			gym.setGymName((char)i+""+(char)i);
			gym.setLocation((char)i+"'s"+" "+"location");
			gyms.add(gym);
		}


		gymRepository.saveAll(gyms);
	}


	@Test
	public void testPagingGyms(){
		//pageNo从0开始
		int pageNo= 5;
		int pageSize = 5;
		//排序相关,封装了排序的信息
		Sort sort = new Sort(Order.desc("id"));
		PageRequest pageable = new PageRequest(pageNo,pageSize,sort);
		Page<Gym> page = gymRepository.findAll(pageable);

		System.out.println("总记录数"+page.getTotalElements());
		System.out.println("当前第几页"+(page.getNumber()+1));
		System.out.println("总页数："+page.getTotalPages());
		System.out.println("当前页面的LIST:"+page.getContent());
		System.out.println("当前页面的记录数"+page.getNumberOfElements());



	}



	@Test
    public void testUpdating(){

        //dataService.updateGymlocation("aa","bjtu");

//        dataService.updateTrainerPosition("cw","BOSS");
//        dataService.updateTrainerEmail("cw","BOSS");
//        dataService.updateTrainerSalary("cw",4578.056);
//        dataService.updateTrainerTelephone("cw","18801130810");
       //dataService.updateUmUserEmail("cw","18801130810");

        //userRepository.updateUmUserEmail("cw","sasdasdas");

    }

    @Test
    public void testChangePassword(){
	    userService.changePassword("cw","啊手动阀手动阀");
    }


    @Test
    public void updateIntro(){
        dataService.updateUserIntro("cw","asdfasdasdfasdf");
    }

	@Test
	public void addTrainer(){
//		Trainer trainer = new Trainer();
//		trainer.setAge(18);
//		trainer.setEmail("ss@qq.com");
//
//		trainer.setName("chz");
//		trainer.setPosition("PE");
//		trainer.setSalary(20000.3);
//		trainer.setTelephone("18801130810");
//
//		dataService.addUserTrainer("cw",trainer);
		dataService.addUserTrainerByID("cw",4);
	}

	@Test
	public void getTrainers(){

		System.out.println(dataService.getUserTrainers("cw").size());
	}
}
