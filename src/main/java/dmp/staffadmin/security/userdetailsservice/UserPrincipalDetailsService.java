package dmp.staffadmin.security.userdetailsservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipalDetailsService implements UserDetailsService
{
	@Autowired private IUserDao userRepository;
	@Autowired private IRoleDao roleDao;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		User user = userRepository.findByUsername(username);
		List<Role> roles = roleDao.findByUsers_Username(user.getUsername());
		user.setRoles(roles);
		return new UserPrincipal(user);
	}

}
