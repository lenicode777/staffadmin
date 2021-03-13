package dmp.staffadmin.security.userdetailsservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dmp.staffadmin.dao.IAgentDao;
import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.metier.enumeration.RoleEnum;
import dmp.staffadmin.security.UserDetailsServiceException;
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
		if(role==null) return user;
		if(!roleDao.existsById(role.getIdRole())) throw new UserDetailsServiceException("Role exitant");
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
	
	//@Bean
	public CommandLineRunner start(IAgentDao agentDao, IRoleDao roleDao)
	{
		return args->
		{
			User Leni = agentDao.findById(9L).get().getUser();
			Role roleAgent = roleDao.findByRole("AGENT");
			roleAgent = roleDao.findById(roleAgent.getIdRole()).get();
			Role roleRespo = roleDao.findByRole(RoleEnum.RESPONSABLE.toString());
			Role roleSaf = roleDao.findByRole(RoleEnum.SAF.toString());
			System.out.println("********************************//////////////////****************************");
			System.out.println("Leni a le role Responsable ?" + Leni.hasRole(RoleEnum.RESPONSABLE.toString()));
			System.out.println("Leni a le role SAF ?" + Leni.hasRole(roleSaf));
			System.out.println("Les roles de LENI sont : ");
			Leni.getRoles().forEach(r->System.out.println(r.getRole()));
			
			System.out.println("RoleRespo = "+ RoleEnum.RESPONSABLE.toString());
			System.out.println("RoleSaf = "+ RoleEnum.SAF.toString());
			addRoleToUser(Leni, roleAgent);
			
			System.out.println("Les roles de LENI sont : ");
			Leni.getRoles().forEach(r->System.out.println(r.getRole()));
		};
	}
	
}