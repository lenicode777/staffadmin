package dmp.staffadmin;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import dmp.staffadmin.dao.IAgentDao;
import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.security.userdetailsservice.IRoleDao;
import dmp.staffadmin.security.userdetailsservice.IUserDao;
import dmp.staffadmin.security.userdetailsservice.Role;
import dmp.staffadmin.security.userdetailsservice.User;

@SpringBootApplication
public class StaffadminApplication 
{
	@Autowired IUserDao userDao;
	@Autowired IAgentDao agentDao;
	@Autowired IRoleDao roleDao;
	@Autowired PasswordEncoder passwordEncoder;
	public static void main(String[] args) 
	{
		SpringApplication.run(StaffadminApplication.class, args);
	}
	
	/*@Bean
	public LayoutDialect thymeleafDialect()
	{
		return new LayoutDialect();
	}*/

}
