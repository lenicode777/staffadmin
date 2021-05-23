package dmp.staffadmin.security.services;

import java.util.List;

import dmp.staffadmin.security.model.AppPrivilege;
import dmp.staffadmin.security.model.AppUser;
import dmp.staffadmin.security.model.UsersRevokedPrivileges;

public interface IUsersRevokedPrivilegesService 
{
	public List<UsersRevokedPrivileges> setUsersRevokedPrivileges(AppUser user, List<AppPrivilege> revokedPrivileges);
	public List<UsersRevokedPrivileges> setUsersRevokedPrivileges(Long idUser, List<AppPrivilege> revokedPrivileges);
	public List<UsersRevokedPrivileges> setUsersRevokedPrivileges(String username, List<AppPrivilege> revokedPrivileges);
}
