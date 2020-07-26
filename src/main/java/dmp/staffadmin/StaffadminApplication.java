package dmp.staffadmin;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import dmp.staffadmin.dao.IAgentDao;
import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.security.userdetailsservice.IRoleDao;
import dmp.staffadmin.security.userdetailsservice.IUserDao;
import dmp.staffadmin.security.userdetailsservice.Role;
import dmp.staffadmin.security.userdetailsservice.User;

@SpringBootApplication
public class StaffadminApplication implements CommandLineRunner
{
	@Autowired IUserDao userDao;
	@Autowired IAgentDao agentDao;
	@Autowired IRoleDao roleDao;
	@Autowired PasswordEncoder passwordEncoder;
	public static void main(String[] args) 
	{
		SpringApplication.run(StaffadminApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception 
	{
		/*List<Role> rolesAgent = Arrays.asList(roleDao.findByRole("AGENT"));
		List<Role> rolesSAF = Arrays.asList(roleDao.findByRole("SAF"), roleDao.findByRole("CSR"), roleDao.findByRole("AGENT"));
		List<Role> rolesRespo = Arrays.asList(roleDao.findByRole("AGENT"), roleDao.findByRole("SD"));
		Agent agent1 = agentDao.getOne(9L);
		Agent agent2 = agentDao.getOne(14L);
		Agent agent3 = agentDao.getOne(16L);
		User user1 = new User(null,"YAPO1", passwordEncoder.encode("1234"), true, rolesRespo, agent1);
		User user2 = new User(null,"YAO2", passwordEncoder.encode("1234"), true, rolesSAF, agent2);
		User user3 = new User(null,"LENI3", passwordEncoder.encode("1234"), true, rolesAgent, agent3);
		//userDao.saveAll(Arrays.asList(user1, user2, user3));
		System.out.println("=============================");
		System.out.println("==============Utilisateurs ajouté avec succès===============");
		System.out.println("=============================");*/
	}
	
	/*@Bean
	public LayoutDialect thymeleafDialect()
	{
		return new LayoutDialect();
	}*/

}
