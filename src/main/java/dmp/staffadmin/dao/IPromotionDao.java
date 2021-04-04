package dmp.staffadmin.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.metier.entities.Fonction;
import dmp.staffadmin.metier.entities.Promotion;

public interface IPromotionDao extends JpaRepository<Promotion, Long> 
{
	public List<Promotion> findByAgentPromu(Agent agentPromu );
	public List<Promotion> findByAgentPromuIdAgent(Long idAgent);
	public List<Promotion> findByNewFonction(Fonction newFonction);
	public List<Promotion> findByNewFonctionIdFonction(Long idFonction);
}
