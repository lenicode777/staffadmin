package dmp.staffadmin.security.userdetailsservice;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dmp.staffadmin.metier.entities.Agent;

public interface IUserDao extends JpaRepository<User, Long>
{
	public User findByUsername(String username);
	public User findByAgent(Agent agent);
	public User findByAgentIdAgent(Long idAgent);
	public List<User> findByRoles_Role(String role);	
}
