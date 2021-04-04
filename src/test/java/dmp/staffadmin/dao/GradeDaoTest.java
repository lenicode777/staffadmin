package dmp.staffadmin.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import dmp.staffadmin.metier.entities.Grade;

@DataJpaTest
@Tag("GradeDao")
public class GradeDaoTest 
{
	@Autowired private IGradeDao gradeDao;	

	@Nested
	class testCrudMethode
	{
		Grade grade ;
		
		@BeforeEach
		void init()
		{
			grade =  new Grade(null, "A4", 5, null);
		}
		
		@Test
		void testSaveMethod()
		{
			gradeDao.save(grade);
		}
	}
}
