package dmp.staffadmin.security.userdetailsservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserMetier implements IUserMetier 
{
	private PasswordEncoder passwordEncoder = this.passwordEncoder();
	@Autowired private IUserDao userDao;
	@Autowired private IRoleDao roleDao;
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public User save(User user) 
	{
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		//System.out.println(passwordEncoder.matches(password, user.getPassword()));
		return userDao.save(user);
	}

	@Override
	public User update(User e) 
	{
		return null;
	}

	@Override
	public User update(Long entityId, User entityBody) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findAll() 
	{
		// TODO Auto-generated method stub
		return null;
	}

}
