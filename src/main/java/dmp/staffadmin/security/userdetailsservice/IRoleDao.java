package dmp.staffadmin.security.userdetailsservice;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleDao extends JpaRepository<Role, Long> 
{
	public Role findByRole(String role);
	public List<Role> findByUsers_Username(String username);
}
