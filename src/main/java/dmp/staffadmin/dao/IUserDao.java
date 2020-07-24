package dmp.staffadmin.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.metier.entities.User;

public interface IUserDao extends JpaRepository<User, Long> 
{
	public List<User> findByUsername(String username);
	public List<User> findByAgent(Agent agent);
}
