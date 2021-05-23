package dmp.staffadmin.security.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dmp.staffadmin.dao.IAgentDao;
import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.metier.enumeration.RoleEnum;
import dmp.staffadmin.security.UserDetailsServiceException;
import dmp.staffadmin.security.dao.AppRoleDao;
import dmp.staffadmin.security.dao.AppUserDao;
import dmp.staffadmin.security.dto.ChangePasswordDto;
import dmp.staffadmin.security.model.AppRole;
import dmp.staffadmin.security.model.AppUser;

@Service
@Transactional
public class UserMetier implements IUserMetier
{
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AppUserDao userDao;
	@Autowired
	private AppRoleDao roleDao;
	@Autowired
	private IAgentDao agentDao;

	

	@Override
	public AppUser save(AppUser user)
	{
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		// System.out.println(passwordEncoder.matches(password, user.getPassword()));
		return userDao.save(user);
	}

	@Override
	public AppUser update(AppUser user)
	{
		return userDao.save(user);
	}

	@Override
	public AppUser update(Long idUser, AppUser user)
	{
		user.setIdUser(idUser);
		return userDao.save(user);
	}

	@Override
	public List<AppUser> findAll()
	{
		return userDao.findAll();
	}

	@Override
	public AppUser addRoleToUser(AppUser user, AppRole role)
	{
		if (role == null)
			return user;
		if (!roleDao.existsById(role.getIdRole()))
			throw new UserDetailsServiceException("Role exitant");
		user.addRole(role);
		return userDao.save(user);
	}

	@Override
	public AppUser removeRoleToUser(AppUser user, AppRole role)
	{
		user.removeRoleToUser(role);
		return userDao.save(user);
	}

	@Override
	public AppUser activateUser(AppUser user)
	{
		user.setActive(true);
		return userDao.save(user);
	}

	@Override
	public AppUser changePassword(ChangePasswordDto userForm)
	{
		if (!userForm.getOldPassword().equals(userForm.getUser().getAgent().getMatricule()))
		{
			throw new RuntimeException("Matricule incorrect");
		} else if (!userForm.getNewPassword().equals(userForm.getConfirmPassword()))
		{
			throw new RuntimeException("mot de passe de confirmation incorrect");
		} else if (userForm.getNewPassword().length() < 8)
		{
			throw new RuntimeException("Le mot de passe doit contenir au moins 8 caractères");
		} else
		{
			userForm.getUser().setPassword(userForm.getNewPassword());
			userForm.getUser().getAgent().setActive(true);
		}
		Agent agent = agentDao.save(userForm.getUser().getAgent());
		userForm.getUser().setAgent(agent);
		return save(userForm.getUser());
	}

	@Override
	public AppUser desactivateUser(AppUser user)
	{
		user.setActive(false);
		user.getAgent().setActive(false);
		Agent agent = agentDao.save(user.getAgent());
		user.setAgent(agent);
		return userDao.save(user);
	}

	@Override
	public boolean hasRole(AppUser user, AppRole role)
	{
		return user.hasRole(role);
	}

	// @Bean
	public CommandLineRunner start(IAgentDao agentDao, AppRoleDao roleDao)
	{
		return args ->
		{
			AppUser Leni = agentDao.findById(9L).get().getUser();
			AppRole roleAgent = roleDao.findByRoleName(RoleEnum.AGENT.toString());
			roleAgent = roleDao.findById(roleAgent.getIdRole()).get();
			AppRole roleRespo = roleDao.findByRoleName(RoleEnum.RESPONSABLE.toString());
			AppRole roleSaf = roleDao.findByRoleName(RoleEnum.SAF.toString());
			System.out.println("********************************//////////////////****************************");
			System.out.println("Leni a le role Responsable ?" + Leni.hasRole(RoleEnum.RESPONSABLE.toString()));
			System.out.println("Leni a le role SAF ?" + Leni.hasRole(roleSaf));
			System.out.println("Les roles de LENI sont : ");
			Leni.getRoles().forEach(r -> System.out.println(r.getRoleName()));

			System.out.println("RoleRespo = " + RoleEnum.RESPONSABLE.toString());
			System.out.println("RoleSaf = " + RoleEnum.SAF.toString());
			addRoleToUser(Leni, roleAgent);

			System.out.println("Les roles de LENI sont : ");
			Leni.getRoles().forEach(r -> System.out.println(r.getRoleName()));
		};
	}

}