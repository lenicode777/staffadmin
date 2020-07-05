package dmp.staffadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import nz.net.ultraq.thymeleaf.LayoutDialect;

@SpringBootApplication
public class StaffadminApplication {

	public static void main(String[] args) 
	{
		SpringApplication.run(StaffadminApplication.class, args);
	}
	
	@Bean
	public LayoutDialect thymeleafDialect()
	{
		return new LayoutDialect();
	}

}
