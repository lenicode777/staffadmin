package dmp.staffadmin.security.services;

import java.util.Collection;
import java.util.List;

import dmp.staffadmin.security.model.AppPrivilege;
import dmp.staffadmin.security.model.AppRole;
import dmp.staffadmin.security.model.AppUser;

public interface IUserAuthoritiesDetailsService 
{
	
	public boolean hasRole(AppUser user, AppRole role);
	public boolean hasRole(Long idUser, AppRole role);
	public boolean hasRole(String username, AppRole role);
	
	public boolean hasPrivilege(String username, AppPrivilege privilege);
	public boolean hasPrivilege(String username, Long idPrivilege);
	public boolean hasPrivilege(AppUser user, AppPrivilege privilege);
	public boolean hasPrivilege(Long idUser, AppPrivilege privilege);
	public boolean hasPrivilege(Long idUser, Long idPrivilege);
	
	public boolean hasAuthority(AppUser user, String authority);
	public boolean hasAuthority(String username, String authority);
	public boolean hasAuthority(Long idUser, String authority);
	
	public List<AppRole> getRoles(AppUser user);
	public List<AppRole> getRoles(String username);
	
	public List<AppPrivilege> getPrivileges(AppUser user);
	public List<AppPrivilege> getPrivileges(String username);
	
	public List<AppPrivilege> getPrivilegesOfRoles(List<Long> rolesId);
	public List<AppPrivilege> getPrivilegesOfRoles(Collection<AppRole> roles);
	
	public List<String> getAuthorities(AppUser user);
	public List<String> getAuthorities(String username);
	
	
}
