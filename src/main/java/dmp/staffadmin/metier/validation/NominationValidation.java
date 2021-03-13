package dmp.staffadmin.metier.validation;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dmp.staffadmin.dao.IAgentDao;
import dmp.staffadmin.dao.IFonctionDao;
import dmp.staffadmin.dao.IUniteAdminDao;
import dmp.staffadmin.metier.entities.Fonction;
import dmp.staffadmin.metier.entities.Nomination;
import dmp.staffadmin.metier.entities.UniteAdmin;
import dmp.staffadmin.metier.exceptions.NominationException;

@Service
public class NominationValidation implements INominationValidation
{
	
	@Autowired private IAgentDao agentDao;
	@Autowired private IFonctionDao fonctionDao;
	@Autowired private IUniteAdminDao uniteAdminDao;

	@Override
	public boolean isValide(Nomination n) 
	{
		return false;
	}

	@Override
	public void validate(Nomination n) 
	{
		Fonction fonctionDeNomination=null;
		UniteAdmin uniteAdminDeNomination=null;
		
		if(n.getAgentNomme()==null) throw new NominationException("Informations sur l'agent non parvenues");
		else
		{
			if(n.getAgentNomme().getIdAgent() != null )
			{
				if(!agentDao.existsById(n.getAgentNomme().getIdAgent()))
					throw new NominationException("Référence à l'agent éronnée");
			}
			else if(n.getAgentNomme().getMatricule() != null)
			{
				if(!agentDao.existsByMatricule(n.getAgentNomme().getMatricule()))
					throw new NominationException("Matricule éronné");
			}
		}
		if(n.getDateNomination()==null) 
		{
			throw new NominationException("Informations sur la date de nomination non parvenues");
		}
		else
		{
			if(n.getDateNomination().after(new Date())) throw new NominationException("La date de nomination ne peut être ultérieure à aujourd'hui");
		}
		
		if(n.getFonctionNomination()==null)
		{
			throw new NominationException("Informations sur la fonction non parvenues");
		}
		else
		{
			if( n.getFonctionNomination().getIdFonction()==null) throw new NominationException("Informations sur le type de fonction non parvenues");
			else
			{
				fonctionDeNomination = fonctionDao.findById(n.getFonctionNomination().getIdFonction()).get();
			}
			
		}
		//if(n.getPostNomination()==null) throw new RuntimeException("Informations sur le post non parvenues");
		if(n.getUniteAdminDeNomination()==null) throw new NominationException("Informations sur le service non parvenues");
		else
		{
			uniteAdminDeNomination = uniteAdminDao.findById(n.getUniteAdminDeNomination().getIdUniteAdmin()).get();
		}
		
		if(uniteAdminDeNomination.getTypeUniteAdmin().getIdTypeUniteAdmin() != fonctionDeNomination.getTypeUniteAdmin().getIdTypeUniteAdmin())
		{
			throw new NominationException("Fonction et unité administrative de nomination incompatibles");
		}
	}
}
