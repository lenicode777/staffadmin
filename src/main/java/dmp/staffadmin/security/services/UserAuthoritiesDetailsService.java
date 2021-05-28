package dmp.staffadmin.security.services;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dmp.staffadmin.security.dao.AppPrivilegeDao;
import dmp.staffadmin.security.dao.AppRoleDao;
import dmp.staffadmin.security.dao.AppUserDao;
import dmp.staffadmin.security.model.AppPrivilege;
import dmp.staffadmin.security.model.AppRole;
import dmp.staffadmin.security.model.AppUser;
import dmp.staffadmin.security.model.UsersRevokedPrivileges;
import dmp.staffadmin.security.utilities.Comparators;

@Service
public class UserAuthoritiesDetailsService implements IUserAuthoritiesDetailsService 
{
	@Autowired private AppUserDao userDao;
	@Autowired private AppRoleDao roleDao;
	@Autowired private AppPrivilegeDao privilegeDao;
	

	
	@Override
	public boolean hasRole(AppUser user, AppRole role) 
	{
		boolean hasRole = user.getRoles().stream()
				  .anyMatch(r->r.getIdRole().longValue()==role.getIdRole().longValue());
		return hasRole;
	}
	
	@Override
	public boolean hasRole(Long idUser, AppRole role) 
	{
		AppUser user = userDao.findById(idUser).get();
		return hasRole(user, role);
	}
	 
	@Override
	public boolean hasRole(String username, AppRole role) 
	{
		AppUser user = userDao.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("Utilisateur introuvable"));
		return hasRole(user, role);
	}

	@Override
	public boolean hasAuthority(AppUser user, String authority) 
	{
		return getAuthorities(user).contains(authority);
	}
	
	@Override
	public boolean hasAuthority(String username, String authority) 
	{
		AppUser user  = userDao.findByUsername(username).get();
		return hasAuthority(user, authority);
	}
	
	@Override
	public boolean hasAuthority(Long idUser, String authority) 
	{
		AppUser user  = userDao.findById(idUser).get();
		return hasAuthority(user, authority);
	}

	@Override
	public List<AppRole> getRoles(String username) 
	{
		AppUser user = userDao.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("Utilisateur introuvable"));
		return getRoles(user);
	}
	
	@Override
	public List<AppPrivilege> getPrivileges(AppUser user) 
	{
		return user.getRoles().stream()
							  .map(AppRole::getPrivileges)
							  .flatMap(Collection::stream)
							  .filter(Comparators.distinctByKey(p -> p.getIdPrivilege().longValue()))
							  .filter
							  (//Je filtre pour retirer de la liste tous les privilèges désactiviés (Revoqués)
								p->!user.getUsersRevokedPrivileges().stream()
																	.map(UsersRevokedPrivileges::getRevokedPrivilege)
																	.map(rp->rp.getIdPrivilege())
																	.anyMatch(rp->rp.longValue()==p.getIdPrivilege().longValue())
							  )
							  .collect(Collectors.toList());
	}

	@Override
	public List<AppPrivilege> getPrivileges(String username) 
	{
		AppUser user = userDao.findByUsername(username)
							  .orElseThrow(()->new UsernameNotFoundException("Utilisateur introuvable"));
		return getPrivileges(user);
	}

	@Override
	public List<String> getAuthorities(String username) 
	{
		return Stream.concat
			   (
					getRoles(username).stream().map(AppRole::getRoleName), 
					getPrivileges(username).stream().map(AppPrivilege::getPrivilegeName)
			   ).collect(Collectors.toList());
	}

	@Override
	public List<AppPrivilege> getPrivilegesOfRoles(List<Long> rolesId) 
	{
		return rolesId.stream().map(idRole->roleDao.findById(idRole).get())
											.map(AppRole::getPrivileges)
											.flatMap(Collection::stream)
											.filter(Comparators.distinctByKey(p -> p.getIdPrivilege()))
											.collect(Collectors.toList());
	}

	@Override
	public List<AppPrivilege> getPrivilegesOfRoles(Collection<AppRole> roles) 
	{
		return roles.stream().map(AppRole::getPrivileges)
							 .flatMap(Collection::stream)
							 .filter(Comparators.distinctByKey(p -> p.getIdPrivilege()))
							 .collect(Collectors.toList());
	}

	@Override
	public boolean hasPrivilege(AppUser user, AppPrivilege privilege) 
	{
		return user.getRoles().stream()
				   .map(AppRole::getPrivileges)
				   .flatMap(Collection::stream)
				   .filter
				   (//On filtre pour ne garder que les privilèges qui ne font pas partir de la liste des privilèges revoqués
						p->user.getUsersRevokedPrivileges().stream().allMatch(rp->p.getIdPrivilege().longValue()!=rp.getRevokedPrivilege().getIdPrivilege().longValue())
				   )
				   .anyMatch(p->p.getIdPrivilege().longValue() ==privilege.getIdPrivilege().longValue());
	}
	
	@Override
	public boolean hasPrivilege(String username, AppPrivilege privilege) 
	{
		AppUser user = userDao.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("Utilisateur introuvable"));
		return hasPrivilege(user, privilege);
	}
	
	@Override
	public boolean hasPrivilege(String username, Long idPrivilege) 
	{
		AppPrivilege privilege = new AppPrivilege();
		privilege.setIdPrivilege(idPrivilege);
		return hasPrivilege(username, privilege);
	}

	@Override
	public boolean hasPrivilege(Long idUser, AppPrivilege privilege) 
	{
		AppUser user = userDao.findById(idUser).get();
		return hasPrivilege(user, privilege);
	}

	@Override
	public boolean hasPrivilege(Long idUser, Long idPrivilege) 
	{
		AppUser user = userDao.findById(idUser).get();
		AppPrivilege privilege = privilegeDao.findById(idUser).get();
		
		return hasPrivilege(user, privilege);
	}

	@Override
	public List<AppRole> getRoles(AppUser user) 
	{
		return (List<AppRole>) user.getRoles();
	}



	@Override
	public List<String> getAuthorities(AppUser user) 
	{
		return Stream.concat
				   (
						getRoles(user).stream().map(AppRole::getRoleName), 
						getPrivileges(user).stream().map(AppPrivilege::getPrivilegeName)
				   ).collect(Collectors.toList());
	}
}
