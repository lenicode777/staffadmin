package dmp.staffadmin.security.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dmp.staffadmin.security.model.AppPrivilege;
import dmp.staffadmin.security.model.AppUser;
import dmp.staffadmin.security.model.UsersRevokedPrivileges;

public interface UsersRevokedPrivilegesDao extends JpaRepository<UsersRevokedPrivileges, Long>
{
	List <UsersRevokedPrivileges> findByRevokedPrivilege(AppPrivilege revokedPrivilege);
	List <UsersRevokedPrivileges> findByRevokedPrivilegeIdPrivilege(Long idRevokedPrivilege);
	List <UsersRevokedPrivileges> findByRevokedPrivilegePrivilegeName(String revokedPrivilegeName);
	
	List<UsersRevokedPrivileges> findByUser(AppUser user);
	List<UsersRevokedPrivileges> findByUserIdUser(Long idUser);
	List<UsersRevokedPrivileges> findByUserUsername(String username);
	
	void deleteByUser(AppUser user);
	void deleteByUserIdUser(Long idUser);
	void deleteByUserUsername(String username);
}
