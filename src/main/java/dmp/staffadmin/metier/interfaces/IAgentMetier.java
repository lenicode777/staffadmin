package dmp.staffadmin.metier.interfaces;

import dmp.staffadmin.metier.entities.Agent;

public interface IAgentMetier extends IAgentExistingRestController, ICrudMetier<Agent>
{
	public Agent affecter(Agent agent);

}
