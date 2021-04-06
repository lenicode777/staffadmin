package dmp.staffadmin.metier.services.interfaces;

import java.util.List;

import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.metier.entities.Recrutement;
import dmp.staffadmin.metier.entities.UniteAdmin;

public interface IAgentMetier extends IAgentExistingRestController, ICrudMetier<Agent>
{
	public Agent affecter(Agent agent, UniteAdmin UAdestination);
	public List<Agent> findActive();
	public List<Agent> findNoneActive();
	public List<Agent> findByPosition(String position);
	public List<Agent> findBySexe(String sexe);
	public List<Agent> findBySituationPresence(String situationPresence);
	public List<Agent> findByStatutAgent(String statutAgent);
	
	public Recrutement recruter(Agent agent);
	
	
}
