package dmp.staffadmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import dmp.staffadmin.dao.IAgentDao;
import dmp.staffadmin.security.userdetailsservice.IRoleDao;
import dmp.staffadmin.security.userdetailsservice.IUserDao;

@SpringBootApplication
public class StaffadminApplication
{
	@Autowired
	IUserDao userDao;
	@Autowired
	IAgentDao agentDao;
	@Autowired
	IRoleDao roleDao;
	@Autowired
	PasswordEncoder passwordEncoder;

	public static void main(String[] args)
	{
		SpringApplication.run(StaffadminApplication.class, args);
	}

	/*
	 * @Bean public LayoutDialect thymeleafDialect() { return new LayoutDialect(); }
	 */

}
