package dmp.staffadmin.security.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import dmp.staffadmin.security.dao.AppRoleDao;
import dmp.staffadmin.security.dao.AppUserDao;
import dmp.staffadmin.security.model.AppRole;
import dmp.staffadmin.security.model.AppUser;

@Component
public class AppUserDetailsManager implements UserDetailsManager
{
	@Autowired
	private AppUserDao userDao;
	
	@Autowired private AppRoleDao roleDao;
	
	@Autowired private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		Optional<AppUser> oUser = userDao.findByUsername(username);
		AppUser user = oUser.orElseThrow(()->new UsernameNotFoundException("username ou mot de passe incorrecte"));
		if(user.getDefaultRoleId() != null)
		{
			AppRole defaultRole = roleDao.findById(user.getDefaultRoleId()).get();
			return new SecurityUser(user, defaultRole);
		}
		return new SecurityUser(user);
	}

	@Override
	public void createUser(UserDetails userDetails) 
	{
		AppUser user = new AppUser();
		user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
		user.setUsername(userDetails.getUsername());
		userDao.save(user);		
	}

	@Override
	public void updateUser(UserDetails userDetails) 
	{
		AppUser user = userDao.findByUsername(userDetails.getUsername()).orElseThrow(()->new UsernameNotFoundException("username incorrecte!"));
		user.setUsername(userDetails.getUsername());		
		userDao.save(user);
	}

	@Override
	public void deleteUser(String username) 
	{
		userDao.deleteByUsername(username);
	}

	@Override
	@Deprecated
	public void changePassword(String oldPassword, String newPassword) 
	{
		
	}
	
	public void changePassword(String username, String oldPassword, String newPassword) 
	{
		AppUser user = userDao.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("username incorrecte!"));
		if(passwordEncoder.matches(oldPassword, user.getPassword()))
		{
			user.setPassword(passwordEncoder.encode(newPassword));
			userDao.save(user);
		}
	}

	@Override
	public boolean userExists(String username) 
	{
		return userDao.existsByUsername(username);
	}

}
