package dmp.staffadmin.metier.services.local;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dmp.staffadmin.dao.IAgentDao;
import dmp.staffadmin.dao.IRecrutementDao;
import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.metier.entities.Recrutement;
import dmp.staffadmin.metier.entities.UniteAdmin;
import dmp.staffadmin.metier.enumeration.EtatRecrutement;
import dmp.staffadmin.metier.interfaces.IAgentMetier;
import dmp.staffadmin.metier.validation.IAgentValidation;

@Service
public class AgentMetier implements IAgentMetier
{
	@Autowired private IAgentDao agentDao;
	@Autowired private IAgentValidation agentValidation;
	@Autowired private IRecrutementDao recrutementDao;
	@Override
	public boolean existingEmail(String email) 
	{
		return !agentDao.findByEmail(email).isEmpty();
	}

	@Override
	public boolean existingTel(String tel) 
	{
		return !agentDao.findByTel(tel).isEmpty();
	}

	@Override
	public boolean existingNumPiece(String numPiece) 
	{
		return!agentDao.findByNumPiece(numPiece).isEmpty();
	}

	@Override
	public boolean existingMatricule(String matricule) 
	{
		return !agentDao.findByMatricule(matricule).isEmpty();
	}

	@Override
	public Agent save(Agent agent)
	{
		agentValidation.validate(agent);
		agent.setActive(true);
		return agentDao.save(agent);
	}

	@Override
	public Recrutement recruter(Agent agent)
	{
		Recrutement recrutement = new Recrutement();
		save(agent);
		recrutement.setAgent(agent);
		recrutement.setDateEnregistrementAgent(new Date());
		recrutement.setStatut(EtatRecrutement.ATTENTE_MUTATION.toString());
		return recrutementDao.save(recrutement);
	}
	@Override
	public Agent update(Agent agent) 
	{
		return save(agent);
	}
	
	@Override
	public Agent update(Long agentId, Agent agentBody)
	{
		agentBody.setIdAgent(agentId);
		save(agentBody);
		return null;
	}

	@Override
	public List<Agent> findAll() 
	{
		return agentDao.findAll();
	}

	@Override
	public Agent affecter(Agent agent, UniteAdmin UAdestination) 
	{
		return null;
	}

	@Override
	public List<Agent> findActive() 
	{
		
		return agentDao.findByActiveTrue();
	}

	@Override
	public List<Agent> findNoneActive() 
	{
		return agentDao.findByActiveFalse();
	}




}
