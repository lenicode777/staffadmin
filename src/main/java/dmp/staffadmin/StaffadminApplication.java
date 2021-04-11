package dmp.staffadmin;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import dmp.staffadmin.dao.IAgentDao;
import dmp.staffadmin.metier.constants.ArchivageConstants;
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

	@Bean
	CommandLineRunner createSystemDirectories()
	{
		return args ->
		{
			File uploadDir = new File(ArchivageConstants.UPLOADS_DIR);
			File agentUploadDir = new File(ArchivageConstants.AGENT_UPLOADS_DIR);
			if (!uploadDir.exists())
			{
				uploadDir.mkdirs();
			}

			if (!agentUploadDir.exists())
			{
				agentUploadDir.mkdirs();
			}
		};
	}
	/*
	 * @Bean public LayoutDialect thymeleafDialect() { return new LayoutDialect(); }
	 */

}
