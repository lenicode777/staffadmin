package dmp.staffadmin.security.dto.services;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import dmp.staffadmin.metier.exceptions.AppRoleException;
import dmp.staffadmin.security.dao.AppRoleDao;
import dmp.staffadmin.security.dto.AuthoritiesDto;
import dmp.staffadmin.security.model.AppPrivilege;
import dmp.staffadmin.security.model.AppRole;
import dmp.staffadmin.security.model.AppUser;
import dmp.staffadmin.security.model.UsersRevokedPrivileges;
import dmp.staffadmin.security.services.AppUserDetailsManager;
import dmp.staffadmin.security.services.SecurityUser;

@Component
public class AuthoritiesDtoManager implements IAuthoritiesDtoManager 
{
	@Autowired private AppUserDetailsManager userDetailsManager;
	@Autowired private AppRoleDao roleDao;
	@Override
	public AuthoritiesDto loadAuthorities(HttpServletRequest request) {
		String username = request
								.getUserPrincipal()
								.getName();
		SecurityUser securityUser = (SecurityUser) userDetailsManager.loadUserByUsername(username);
		
		AppUser user = securityUser.getUser();
		Collection<AppRole> roles = user.getRoles();
		Collection<AppPrivilege> revokedPrivileges = user.getUsersRevokedPrivileges().stream().map(UsersRevokedPrivileges::getRevokedPrivilege).collect(Collectors.toList());
		AppRole defaultRole=null;
		Long defaultRoleId = user.getDefaultRoleId();
		if(defaultRoleId != null)
		{
			if(roleDao.existsById(defaultRoleId))
			{
				defaultRole = roleDao.findById(user.getDefaultRoleId()).get();
			}
		}
		AuthoritiesDto authDto = new AuthoritiesDto();
		authDto.setSecurityUser(securityUser);
		authDto.setRoles(roles);
		authDto.setRevokedPrivileges(revokedPrivileges);
		authDto.setDefaultRole(defaultRole);
		
		return authDto;
	}
	
}
