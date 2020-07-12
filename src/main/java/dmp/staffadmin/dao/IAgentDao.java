package dmp.staffadmin.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dmp.staffadmin.metier.entities.Agent;

public interface IAgentDao extends JpaRepository<Agent, Long> 
{
	public List<Agent> findByEmail(String email);
	public List<Agent> findByTel(String tel);
	public List<Agent> findByNumPiece(String numPiece);
	public List<Agent> findByMatricule(String matricule);
	public List<Agent> findByActiveTrue();
	public List<Agent> findByActiveFalse();
}
