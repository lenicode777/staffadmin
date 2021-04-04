package dmp.staffadmin.metier.validation;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dmp.staffadmin.dao.IAgentDao;
import dmp.staffadmin.dao.IEmploiDao;
import dmp.staffadmin.dao.IFonctionDao;
import dmp.staffadmin.dao.IGradeDao;
import dmp.staffadmin.metier.entities.Promotion;
import dmp.staffadmin.metier.exceptions.PromotionException;

@Service
public class PromotionValidation implements IValidation<Promotion>
{
	@Autowired private IAgentDao agentDao;
	@Autowired private IFonctionDao fonctionDao;
	@Autowired private IEmploiDao emploiDao;
	@Autowired private IGradeDao gradeDao;

	@Override
	public boolean isValide(Promotion e) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Promotion promotion) throws PromotionException
	{
		Long idAgent = promotion.getAgentPromu().getIdAgent();
		Long idNewFonction = promotion.getNewFonction().getIdFonction();
		Long idNewEmploi = promotion.getNewEmploi().getIdEmploi();
		Long idNewGrade = promotion.getNewGrade().getIdGrade();
		
		if(promotion.getDatePromotion().after(new Date())) throw new PromotionException("La date de promotion ne peut être ultérieure à aujourd'hui");		
		if(idAgent.longValue()==0 || idAgent==null) throw new PromotionException("Informations sur l'agent non parvenues");
		if(idNewFonction.longValue()==0 || idNewFonction==null) throw new PromotionException("Informations sur la fonction non parvenues");
		if(idNewEmploi.longValue()==0 || idNewEmploi==null) throw new PromotionException("Informations sur l'emploi non parvenues");
		if(idNewGrade.longValue()==0 || idNewGrade==null) throw new PromotionException("Informations sur le grade non parvenues");
		
		if(!agentDao.existsById(idAgent)) throw new PromotionException("Référence de l'agent incorrecte");
		if(!fonctionDao.existsById(idNewFonction)) throw new PromotionException("Référence de la fonction incorrecte");
		if(!emploiDao.existsById(idNewEmploi)) throw new PromotionException("Référence de l'emploi incorrecte");
		if(!gradeDao.existsById(idNewGrade)) throw new PromotionException("Référence du grade incorrecte");
		
	}

}
