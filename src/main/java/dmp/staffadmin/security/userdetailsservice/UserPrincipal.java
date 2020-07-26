package dmp.staffadmin.security.userdetailsservice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails
{
	private static final long serialVersionUID = 1L;
	private User user;
	//@Autowired private IRoleDao roleDao;
	
	public UserPrincipal(User user) 
	{
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() 
	{
		/*user.getRoles().forEach(role->
		{
			System.out.println(role.getIdRole());
		});*/
		List<GrantedAuthority> authorities = new ArrayList<>();
		//List<Role> roles = roleDao.findByUsers_Username(user.getUsername());
		user.getRoles().forEach(role->
		{
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole()));
		});
		return authorities;
	}

	@Override
	public String getPassword() 
	{
		return user.getPassword();
	}

	@Override
	public String getUsername() 
	{
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() 
	{
		return true;
	}

	@Override
	public boolean isAccountNonLocked() 
	{
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() 
	{
		return true;
	}

	@Override
	public boolean isEnabled() 
	{
		return user.isActive();
	}
}
