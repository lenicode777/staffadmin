package dmp.staffadmin.security.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import dmp.staffadmin.security.model.AppRole;

public interface AppRoleDao extends JpaRepository<AppRole, Long> 
{
	public AppRole findByRoleName(String role);

	public List<AppRole> findByUsers_Username(String username);
}
