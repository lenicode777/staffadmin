package dmp.staffadmin.metier.interfaces;

import java.util.List;

import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.metier.entities.Recrutement;
import dmp.staffadmin.metier.entities.UniteAdmin;

public interface IAgentMetier extends IAgentExistingRestController, ICrudMetier<Agent>
{
	public Agent affecter(Agent agent, UniteAdmin UAdestination);
	public List<Agent> findActive();
	public List<Agent> findNoneActive();
	public Recrutement recruter(Agent agent);
}
