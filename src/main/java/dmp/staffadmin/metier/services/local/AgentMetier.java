package dmp.staffadmin.metier.services.local;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dmp.staffadmin.dao.IAgentDao;
import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.metier.interfaces.IAgentMetier;

@Service
public class AgentMetier implements IAgentMetier
{
	@Autowired private IAgentDao agentDao;
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
	public Agent save() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Agent update(Agent e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Agent> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Agent affecter(Agent agent) {
		// TODO Auto-generated method stub
		return null;
	}

}
