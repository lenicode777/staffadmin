package dmp.staffadmin.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import dmp.staffadmin.dao.IAgentDao;
import dmp.staffadmin.metier.entities.Agent;

@RestController
public class AgentRestController 
{
	@Autowired private IAgentDao agentDao;
	
	@GetMapping(path = "/staffadmin/agentRestController/findByMatricule/{matricule}")
	public Agent findByMatricule(@PathVariable String matricule)
	{
		Agent agent = agentDao.findByMatricule(matricule);
		System.out.println("MATRICULE = " + matricule);
		System.out.println(agent);
		return agent;
	}
}
