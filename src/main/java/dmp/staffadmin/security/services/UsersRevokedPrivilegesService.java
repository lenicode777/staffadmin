package dmp.staffadmin.security.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dmp.staffadmin.security.dao.AppUserDao;
import dmp.staffadmin.security.dao.UsersRevokedPrivilegesDao;
import dmp.staffadmin.security.model.AppPrivilege;
import dmp.staffadmin.security.model.AppUser;
import dmp.staffadmin.security.model.UsersRevokedPrivileges;

@Service
public class UsersRevokedPrivilegesService implements IUsersRevokedPrivilegesService 
{
	@Autowired private UsersRevokedPrivilegesDao usersRevokedPrivilegesDao;
	@Autowired private AppUserDao userDao;
	@Override
	public List<UsersRevokedPrivileges> setUsersRevokedPrivileges(AppUser user,
			List<AppPrivilege> revokedPrivileges) 
	{
		List<UsersRevokedPrivileges> usersRevokedPrivileges = revokedPrivileges.stream()
				   .map(rp->new UsersRevokedPrivileges(null, user, rp))
				   .collect(Collectors.toList());
		usersRevokedPrivilegesDao.deleteByUser(user);
		return usersRevokedPrivilegesDao.saveAll(usersRevokedPrivileges);
		
	}

	@Override
	public List<UsersRevokedPrivileges> setUsersRevokedPrivileges(Long idUser,
			List<AppPrivilege> revokedPrivileges) 
	{
		AppUser user = new AppUser(); 
		user.setIdUser(idUser);
		return setUsersRevokedPrivileges(user, revokedPrivileges);
	}

	@Override
	public List<UsersRevokedPrivileges> setUsersRevokedPrivileges(String username,
			List<AppPrivilege> revokedPrivileges) 
	{
		AppUser user = userDao.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Nom d'utilisateur introuvable"));
		return setUsersRevokedPrivileges(user, revokedPrivileges);
	}

}
