package dmp.staffadmin.metier.services.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dmp.staffadmin.dao.INominationDao;
import dmp.staffadmin.metier.entities.Nomination;
import dmp.staffadmin.metier.services.interfaces.INominationMetier;
import dmp.staffadmin.metier.services.interfaces.IUniteAdminMetier;
import dmp.staffadmin.metier.validation.INominationValidation;
@Service @Transactional
public class NominationMetier implements INominationMetier 
{
	/*
	@Autowired private IFonctionDao fonctionDao;
	@Autowired private IAgentDao agentDao;
	@Autowired private IPostDao postDao;
	@Autowired private IRoleDao roleDao;
	@Autowired private IUserDao userDao;
	@Autowired private IUniteAdminDao uniteAdminDao;
	*/
	
	@Autowired private INominationDao nominationDao;
	@Autowired private IUniteAdminMetier uniteAdminMetier;
	@Autowired private INominationValidation nominationValidation;
	
	@Override @Transactional
	public Nomination save(Nomination nomination) // Fonction très complexe
	{
		nomination.getAgentNomme().setFonction(nomination.getFonctionNomination());
		nominationValidation.validate(nomination);
		System.out.println("===============================Validation OK===============================");
		
		uniteAdminMetier.nommerResponsable(nomination.getUniteAdminDeNomination(), nomination.getAgentNomme(), nomination.getFonctionNomination());
		
		return nominationDao.save(nomination);
	}

	@Override
	public Nomination update(Nomination e) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Nomination update(Long entityId, Nomination entityBody) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Nomination> findAll() 
	{
		// TODO Auto-generated method stub
		return null;
	}

}
