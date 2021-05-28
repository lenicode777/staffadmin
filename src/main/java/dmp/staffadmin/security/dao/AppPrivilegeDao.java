package dmp.staffadmin.security.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dmp.staffadmin.security.model.AppPrivilege;

public interface AppPrivilegeDao extends JpaRepository<AppPrivilege, Long>
{
	public AppPrivilege findByPrivilegeName(String privilegeName);
	public List<AppPrivilege> findByRoles_idRole(Long idRole);
}
