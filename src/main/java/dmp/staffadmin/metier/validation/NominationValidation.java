package dmp.staffadmin.metier.validation;

import java.util.Date;

import org.springframework.stereotype.Service;

import dmp.staffadmin.metier.entities.Nomination;

@Service
public class NominationValidation implements INominationValidation
{

	@Override
	public boolean isValide(Nomination n) 
	{
		return false;
	}

	@Override
	public void validate(Nomination n) 
	{
		if(n.getAgentNomme()==null) throw new RuntimeException("Informations sur l'agent non parvenues");
		if(n.getDateNomination()==null) 
		{
			throw new RuntimeException("Informations sur la date de nomination non parvenues");
		}
		else
		{
			if(n.getDateNomination().after(new Date())) throw new RuntimeException("Date de nomination érronées");
		}
		
		if(n.getFonctionNomination()==null)
		{
			throw new RuntimeException("Informations sur la fonction non parvenues");
		}
		else
		{
			//if(n.getFonctionNomination().getTypeUniteAdmin()==null || n.getFonctionNomination().getIdFonction()==null) throw new RuntimeException("Informations sur le type de fonction non parvenues");
			//if(n.getFonctionNomination().getRoleAssocie()==null) throw new RuntimeException("Informations sur le type de fonction non parvenues");
			
		}
		//if(n.getPostNomination()==null) throw new RuntimeException("Informations sur le post non parvenues");
		if(n.getUniteAdminDeNomination()==null) throw new RuntimeException("Informations sur le service non parvenues");

	}

}
