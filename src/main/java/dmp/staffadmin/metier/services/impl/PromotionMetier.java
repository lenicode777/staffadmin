package dmp.staffadmin.metier.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dmp.staffadmin.dao.IAgentDao;
import dmp.staffadmin.dao.IPromotionDao;
import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.metier.entities.Promotion;
import dmp.staffadmin.metier.exceptions.PromotionException;
import dmp.staffadmin.metier.services.interfaces.IPromotionMetier;
import dmp.staffadmin.metier.validation.IPromotionValidation;
import dmp.staffadmin.metier.validation.IValidation;

@Service @Transactional
public class PromotionMetier implements IPromotionMetier 
{
	@Autowired private IPromotionDao promotionDao;
	@Autowired private IAgentDao agentDao;
	@Autowired private IValidation<Promotion> promotionValidation;

	@Override
	public Promotion save(Promotion promotion) throws PromotionException
	{
		promotionValidation.validate(promotion);
		Agent agentPromu = agentDao.findById(promotion.getAgentPromu().getIdAgent()).get();
		
		promotion.setHoldFonction(agentPromu.getFonction());
		promotion.setHoldEmploi(agentPromu.getEmploi());
		promotion.setHoldGrade(agentPromu.getGrade());
		
		agentPromu.setFonction(promotion.getNewFonction());
		agentPromu.setEmploi(promotion.getNewEmploi());
		agentPromu.setGrade(promotion.getNewGrade());
		
		agentDao.save(agentPromu);
		return promotionDao.save(promotion);
	}

	@Override
	public Promotion update(Promotion e) 
	{
		return null;
	}

	@Override
	public Promotion update(Long entityId, Promotion entityBody) 
	{
		return null;
	}

	@Override
	public List<Promotion> findAll() 
	{
		return null;
	}

}
