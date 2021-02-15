package dmp.staffadmin.security.userdetailsservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dmp.staffadmin.dao.IAgentDao;
import dmp.staffadmin.metier.entities.Agent;
import lombok.Data;

@Service @Transactional
public class UserMetier implements IUserMetier 
{
	private PasswordEncoder passwordEncoder = this.passwordEncoder();
	@Autowired private IUserDao userDao;
	@Autowired private IRoleDao roleDao;
	@Autowired private IAgentDao agentDao;
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
	public User update(User user) 
	{
		return userDao.save(user);
	}

	@Override
	public User update(Long idUser, User user) 
	{
		user.setIdUser(idUser);
		return userDao.save(user);
	}

	@Override
	public List<User> findAll() 
	{
		return userDao.findAll();
	}

	@Override
	public User addRoleToUser(User user, Role role) 
	{
		user.addRole(role);
		return userDao.save(user);
	}

	@Override
	public User removeRoleToUser(User user, Role role) 
	{
		user.removeRoleToUser(role);
		return userDao.save(user);
	}

	@Override
	public User activateUser(User user) 
	{
		user.setActive(true);
		return userDao.save(user);
	}
	
	@Override
	public User changePassword(UserForm userForm)
	{		
		if(! userForm.getOldPassword().equals(userForm.getUser().getAgent().getMatricule()))
		{
			throw new RuntimeException("Matricule incorrect");
		}
		else if(!userForm.getNewPassword().equals(userForm.getConfirmPassword()))
		{
			throw new RuntimeException("mot de passe de confirmation incorrect");
		}
		else if(userForm.getNewPassword().length()<8)
		{
			throw new RuntimeException("Le mot de passe doit contenir au moins 8 caractères");
		}
		else
		{
			userForm.getUser().setPassword(userForm.getNewPassword());
			userForm.getUser().getAgent().setActive(true);
		}
		Agent agent = agentDao.save(userForm.getUser().getAgent());
		userForm.getUser().setAgent(agent);
		return save(userForm.getUser());
	}

	@Override
	public User desactivateUser(User user) 
	{
		user.setActive(false);
		user.getAgent().setActive(false);
		Agent agent = agentDao.save(user.getAgent());
		user.setAgent(agent);
		return userDao.save(user);
	}

	@Override
	public boolean hasRole(User user, Role role) 
	{
		return user.hasRole(role);
	}
}