package dmp.staffadmin.security.dto.services;

import java.util.List;

import dmp.staffadmin.security.dto.PrivilegeAccessDto;
import dmp.staffadmin.security.dto.UserAccessDto;
import dmp.staffadmin.security.model.AppUser;

public interface IUserAccessDtoManager 
{
	public void setRoles(List<Long> rolesIds, AppUser user);
	public void setRevokedPrivileges(List<PrivilegeAccessDto> privilegeAccessDtos, AppUser user);
	public void setChampVisuel(Long idChampVisuel, AppUser user);
	public void setActive(boolean active, AppUser user);
	
	public UserAccessDto setGlobalsAccessParams(UserAccessDto userAccessDto);
}