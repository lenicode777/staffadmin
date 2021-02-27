package dmp.staffadmin.metier.validation;

import java.util.Date;

import org.springframework.stereotype.Service;

import dmp.staffadmin.metier.entities.Affectation;
import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.metier.entities.UniteAdmin;
import dmp.staffadmin.metier.exceptions.AffectationException;

@Service
public class AffectationValidation implements IValidation<Affectation> 
{

	@Override
	public boolean isValide(Affectation e) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Affectation affectation) 
	{
		Agent agentAAffecter = affectation.getAgent();
		UniteAdmin uaArrivee = affectation.getUaArrivee();
		UniteAdmin uaDepart = agentAAffecter.getTutelleDirecte();
		
		if(agentAAffecter==null)
		{
			throw new AffectationException("Informations sur l'agent non fournies");
		}
		
		if(affectation.getDateAffectation()==null)
		{
			throw new AffectationException("Date de l'affectation non fournies");
		}
		else if(affectation.getDateAffectation().after(new Date()))
		{
			throw new AffectationException("La date d'affectation ne peut être ultérieure à aujourd'hui");
		}
			
		
		if(agentAAffecter.getPost()!=null)
		{
			if(agentAAffecter.getPost().getFonction().isFonctionDeNomination())
			{
				throw new AffectationException("L'agent que vous essayer d'affecter est à un post de responsabilité.");
			}
		}
		
		if(uaDepart == null)
		{
			throw new AffectationException("Service de d'origine non fournie");
		}
		
		if(uaArrivee == null)
		{
			throw new AffectationException("Service de destination non fournie");
		}
	}

}
