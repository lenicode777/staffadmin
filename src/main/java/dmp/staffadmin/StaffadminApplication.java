package dmp.staffadmin;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import dmp.staffadmin.dao.IAgentDao;
import dmp.staffadmin.metier.constants.ArchivageConstants;
import dmp.staffadmin.security.dao.AppRoleDao;
import dmp.staffadmin.security.dao.AppUserDao;

@SpringBootApplication
@ComponentScan(basePackages = {"dmp.staffadmin", "dmp.staffadmin.security.config"})
public class StaffadminApplication
{
	@Autowired
	AppUserDao userDao;
	@Autowired
	IAgentDao agentDao;
	@Autowired
	AppRoleDao roleDao;

	public static void main(String[] args)
	{
		SpringApplication.run(StaffadminApplication.class, args);
	}

	@Bean
	CommandLineRunner createSystemDirectories()
	{
		return args ->
		{
			//AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SecurityConfig.class);
			
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
