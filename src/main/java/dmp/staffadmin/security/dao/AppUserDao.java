package dmp.staffadmin.security.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.security.model.AppUser;

public interface AppUserDao extends JpaRepository<AppUser, Long>
{
	public Optional<AppUser> findByUsername(String username);

	public Optional<AppUser> findByAgent(Agent agent);

	public Optional<AppUser> findByAgentIdAgent(Long idAgent);
	public Optional<AppUser> findByAgentMatricule(String matricule);

	public List<AppUser> findByRoles_RoleName(String role);
	
	public void deleteByUsername(String username);
	public boolean existsByUsername(String username);
}
