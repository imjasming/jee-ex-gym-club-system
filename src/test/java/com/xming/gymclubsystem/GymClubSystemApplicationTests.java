package com.xming.gymclubsystem;

import com.xming.gymclubsystem.domain.Role;
import com.xming.gymclubsystem.domain.UmUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static com.xming.gymclubsystem.domain.Role.RoleName.ROLE_USER;

/**
 * 使用Spring提供的Junit的运行器
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class GymClubSystemApplicationTests {


	@Autowired
	private EntityManager em;


	@Test
	@Transactional
	@Rollback(false)
	public void contextLoads() {
		Role role1 = new Role(ROLE_USER);
        //Role role2 = new Role(ROLE_USER);

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

		/*role2.getUsers().add(user1);
		role2.getUsers().add(user2);

		user1.getRoles().add(role1);
		user1.getRoles().add(role2);

		user2.getRoles().add(role1);
		user2.getRoles().add(role2);*/


        //保存

		em.persist(role1);
        //em.persist(role2);
		em.persist(user1);
		em.persist(user2);





	}

}
