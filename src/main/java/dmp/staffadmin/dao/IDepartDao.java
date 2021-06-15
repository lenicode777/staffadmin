package dmp.staffadmin.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.metier.entities.Depart;
import dmp.staffadmin.metier.entities.TypeDepart;

public interface IDepartDao extends JpaRepository<Depart, Long> 
{
	List<Depart> findByTypeDepart(TypeDepart typeDepart);
	List<Depart> findByTypeDepartNomType(String nomType);
	List<Depart> findByAgent(Agent agent);
	List<Depart> findByTypeDepartId(Long idTypeDepart);
	Depart findByAgentMatricule(String matricule);
	Depart findByAgentIdAgent(Long idAgent);
	boolean existsByAgentIdAgent(long idAgent);
}
