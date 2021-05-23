package dmp.staffadmin.security.services;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.security.dao.AppRoleDao;
import dmp.staffadmin.security.model.AppPrivilege;
import dmp.staffadmin.security.model.AppRole;
import dmp.staffadmin.security.model.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class SecurityUser implements UserDetails
{
	private AppUser user;
	private AppRole defaultRole;
	
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() 
	{
		List<GrantedAuthority> authorities;
		
		if(defaultRole!=null)
		{
			authorities = Stream.concat(Stream.of(defaultRole.getRoleName()), defaultRole.getPrivileges().stream().map(AppPrivilege::getPrivilegeName))
								.map(auth->new SimpleGrantedAuthority(auth)).collect(Collectors.toList());
		}
		else
		{
			Stream<String> rolesStream = user.getRoles()
											 .stream()
											 .map(AppRole::getRoleName);

			Stream<String> privilegeStream = user.getRoles()
												 .stream().map(AppRole::getPrivileges)
												 .flatMap(privilegesList->privilegesList.stream())
												 .map(AppPrivilege::getPrivilegeName);

			authorities = Stream.concat(rolesStream, privilegeStream)
							   .map(authority->new SimpleGrantedAuthority(authority))
							   .collect(Collectors.toList());
		}
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
		return true;
	}

	public Agent getAgent()
	{
		return user.getAgent();
	}

	public SecurityUser(AppUser user) {
		super();
		this.user = user;
	}
}
