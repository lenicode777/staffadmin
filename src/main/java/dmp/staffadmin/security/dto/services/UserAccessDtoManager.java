package dmp.staffadmin.security.dto.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dmp.staffadmin.security.dao.AppPrivilegeDao;
import dmp.staffadmin.security.dao.AppRoleDao;
import dmp.staffadmin.security.dao.AppUserDao;
import dmp.staffadmin.security.dao.UsersRevokedPrivilegesDao;
import dmp.staffadmin.security.dto.PrivilegeAccessDto;
import dmp.staffadmin.security.dto.UserAccessDto;
import dmp.staffadmin.security.model.AppPrivilege;
import dmp.staffadmin.security.model.AppRole;
import dmp.staffadmin.security.model.AppUser;
import dmp.staffadmin.security.model.UsersRevokedPrivileges;
import dmp.staffadmin.security.services.IUsersRevokedPrivilegesService;
import lombok.Setter;

@Service
public class UserAccessDtoManager implements IUserAccessDtoManager 
{
	@Autowired private AppUserDao userDao;
	@Autowired private AppRoleDao roleDao;
	@Autowired private AppPrivilegeDao privilegeDao;
	@Autowired private UsersRevokedPrivilegesDao usersRevokedPrivilegesDao; 
	@Autowired private IUsersRevokedPrivilegesService usersRevokedPrivilegesService;

	@Override
	public void setRoles(List<Long> rolesIds, AppUser user) 
	{
		//AppUser user = userDao.findById(userAccessDto.getIdUser()).get();
		List<AppRole> roles = rolesIds.stream()
									  .map(idRole->roleDao.findById(idRole).get())
									  .collect(Collectors.toList());
		user.setRoles(roles);
		userDao.save(user);
	}

	@Override
	public void setRevokedPrivileges(List<PrivilegeAccessDto> privilegeAccessDtos, AppUser user) 
	{
		//AppUser user = userDao.findById(userAccessDto.getIdUser()).get();
		List<AppPrivilege> revokedPrivileges = privilegeAccessDtos.stream()
																  .filter(pad->!pad.isNotRevoked())
																  .map(pad->privilegeDao.findById(pad.getIdPrivilege()).get())
																  .collect(Collectors.toList());
		revokedPrivileges.forEach(privilege->
		{
			System.out.println();
			System.out.println("========================");
			System.out.println("Privilege = " + privilege.getPrivilegeName());
			System.out.println("idPrivilege = " + privilege.getIdPrivilege());
			//System.out.println("notRevoked = " + notRevoked);
			System.out.println("========================");
			System.out.println();
		});
		
		usersRevokedPrivilegesService.setUsersRevokedPrivileges(user, revokedPrivileges);
	}

	@Override
	public void setChampVisuel(Long idChampVisuel, AppUser user) 
	{
		//AppUser user = userDao.findById(userAccessDto.getIdUser()).get();
		user.setIdUaChampVisuel(idChampVisuel);
		userDao.save(user);
	}

	@Override
	public void setActive(boolean active, AppUser user) 
	{
		//AppUser user = userDao.findById(userAccessDto.getIdUser()).get();
		user.setActive(active);
		userDao.save(user);
	}

	@Override
	@Transactional(rollbackFor = {Exception.class,  RuntimeException.class})
	public UserAccessDto setGlobalsAccessParams(UserAccessDto userAccessDto) 
	{
		System.out.println("IdUser = " + userAccessDto.getIdUser());
		AppUser user = userDao.findById(userAccessDto.getIdUser()).get();
		setRoles(userAccessDto.getRolesIds(), user);
		setRevokedPrivileges(userAccessDto.getPrivilegeAccessDtos(), user);
		setChampVisuel(userAccessDto.getIdUaChampVisuel(), user);
		setActive(userAccessDto.isActive(), user);
		
		return userAccessDto;
	}
}
