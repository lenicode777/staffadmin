package dmp.staffadmin.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import dmp.staffadmin.metier.interfaces.IAgentExistingRestController;
import dmp.staffadmin.metier.interfaces.IAgentMetier;
@RestController
public class AgentExistingRestController implements IAgentExistingRestController 
{
	@Autowired private IAgentMetier agentMetier;
	@GetMapping("/staffadmin/exists/email/{email}")
	@Override
	public boolean existingEmail(@PathVariable String email) 
	{
		return agentMetier.existingEmail(email);
	}

	@GetMapping("/staffadmin/exists/tel/{tel}")
	@Override
	public boolean existingTel(@PathVariable String tel) 
	{
		return agentMetier.existingTel(tel);
	}

	@GetMapping("/staffadmin/exists/numPiece/{numPiece}")
	@Override
	public boolean existingNumPiece(@PathVariable String numPiece) 
	{
		return agentMetier.existingNumPiece(numPiece);
	}

	@GetMapping("/staffadmin/exists/matricule/{matricule}")
	@Override
	public boolean existingMatricule(@PathVariable String matricule) 
	{
		return agentMetier.existingMatricule(matricule);
	}
}
