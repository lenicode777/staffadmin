package dmp.staffadmin.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dmp.staffadmin.metier.interfaces.IAgentExistingRestController;
import dmp.staffadmin.metier.interfaces.IAgentMetier;
@RestController
public class AgentExistingRestController ///implements IAgentExistingRestController 
{
	@Autowired private IAgentMetier agentMetier;
	@GetMapping("/staffadmin/exists/email/{email}")
	public boolean existingEmail(@PathVariable String email, @RequestParam(defaultValue = "0") Long idAgent) 
	{
		if(idAgent==0 || idAgent==null)
		{
			return agentMetier.existingEmail(email);
		}
		else
		{
			return agentMetier.existingEmail(email, idAgent);
		}		
	}
	
	@GetMapping("/staffadmin/exists/emailPro/{emailPro}")
	public boolean existingEmailPro(@PathVariable String emailPro, @RequestParam(defaultValue = "0") Long idAgent) 
	{
		if(idAgent==0 || idAgent==null)
		{
			return agentMetier.existingEmailPro(emailPro);
		}
		else
		{
			return agentMetier.existingEmailPro(emailPro, idAgent);
		}		
	}

	@GetMapping("/staffadmin/exists/tel/{tel}")
	public boolean existingTel(@PathVariable String tel, @RequestParam(defaultValue = "0") Long idAgent) 
	{
		if(idAgent==0 || idAgent==null)
		{
			return agentMetier.existingTel(tel);
		}
		else
		{
			return agentMetier.existingTel(tel, idAgent);
		}
	}

	@GetMapping("/staffadmin/exists/numPiece/{numPiece}")
	public boolean existingNumPiece(@PathVariable String numPiece, @RequestParam(defaultValue = "0") Long idAgent) 
	{
		if(idAgent==0 || idAgent==null)
		{
			return agentMetier.existingNumPiece(numPiece);
		}
		else
		{
			return agentMetier.existingNumPiece(numPiece, idAgent);
		}
		
	}

	@GetMapping("/staffadmin/exists/matricule/{matricule}")
	public boolean existingMatricule(@PathVariable String matricule, @RequestParam(defaultValue = "0") Long idAgent) 
	{
		if(idAgent==0 || idAgent==null)
		{
			return agentMetier.existingMatricule(matricule);
		}
		else
		{
			return agentMetier.existingMatricule(matricule, idAgent);
		}
		
	}
	
	@GetMapping("/staffadmin/exists/numBadge/{numBadge}")
	public boolean existingNumBadge(@PathVariable String numBadge, @RequestParam(defaultValue = "0") Long idAgent) 
	{
		if(idAgent==0 || idAgent==null)
		{
			return agentMetier.existingNumBadge(numBadge);
		}
		else
		{
			return agentMetier.existingNumBadge(numBadge, idAgent);
		}		
	}
}
