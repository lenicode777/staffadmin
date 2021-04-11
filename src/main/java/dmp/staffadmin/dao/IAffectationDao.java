package dmp.staffadmin.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dmp.staffadmin.metier.entities.Affectation;

public interface IAffectationDao extends JpaRepository<Affectation, Long>
{
	public List<Affectation> findByAgentIdAgent(Long idAgent);

	public List<Affectation> findByUaArriveeIdUniteAdmin(Long idUniteAdmin);

	public List<Affectation> findByUaDepartIdUniteAdmin(Long idUniteAdmin);
}
