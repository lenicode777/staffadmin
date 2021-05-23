package dmp.staffadmin.security.services;

import dmp.staffadmin.security.model.AppPrivilege;
import dmp.staffadmin.security.model.AppRole;

public interface IUserAuthoritiesDetailsManager extends IUserAuthoritiesDetailsService
{	
	public void addRole(String username, AppRole role);
	public void addPrivilege(String username, AppPrivilege privilege);
	public void addAuthority(String username, String authority);
	
	public void removeRole(String username, AppRole role);
	public void removePrivilege(String username, AppPrivilege privilege);
	public void removeAuthority(String username, String authority);
	
	public void revokePrivilege(String username, AppPrivilege privilege);
	public void restorePrivilege(String username, AppPrivilege privilege);
	
	public void setRoleAsDefault(String username, AppRole role);
	
}
