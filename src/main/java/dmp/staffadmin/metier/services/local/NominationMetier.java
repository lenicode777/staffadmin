package dmp.staffadmin.metier.services.local;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dmp.staffadmin.dao.IAgentDao;
import dmp.staffadmin.dao.IFonctionDao;
import dmp.staffadmin.dao.INominationDao;
import dmp.staffadmin.dao.IPostDao;
import dmp.staffadmin.dao.IUniteAdminDao;
import dmp.staffadmin.metier.entities.Nomination;
import dmp.staffadmin.metier.entities.Post;
import dmp.staffadmin.metier.interfaces.INominationMetier;
@Service @Transactional
public class NominationMetier implements INominationMetier 
{
	@Autowired private IFonctionDao fonctionDao;
	@Autowired private IAgentDao agentDao;
	@Autowired private IPostDao postDao;
	@Autowired private INominationDao nominationDao;
	@Autowired private IUniteAdminDao uniteAdminDao;
	
	@Override @Transactional
	public Nomination save(Nomination nomination) 
	{
		nomination.setFonctionNomination(fonctionDao.getOne(nomination.getFonctionNomination().getIdFonction()));
		nomination.setUniteAdminDeNomination(uniteAdminDao.getOne(nomination.getUniteAdminDeNomination().getIdUniteAdmin()));
		nomination.setAgentNomme(agentDao.getOne(nomination.getAgentNomme().getIdAgent()));
		nomination.getAgentNomme().setTutelleDirecte(nomination.getUniteAdminDeNomination());
		agentDao.save(nomination.getAgentNomme());
		//System.out.println(nomination);
		//Post postManager = new Post();
		if(nomination.getFonctionNomination().isFonctionTopManager())
		{
			Post postManager = new Post(nomination.getUniteAdminDeNomination().getPostManager().getIdPost(), nomination.getFonctionNomination(), nomination.getTitreNomination(), nomination.getUniteAdminDeNomination(), nomination.getAgentNomme());
			//postManager = postDao.save(postManager);
			nomination.getUniteAdminDeNomination().setPostManager(postManager);
		}
		else
		{
			Post postDeResponsabilite = new Post(null, nomination.getFonctionNomination(), nomination.getTitreNomination(), nomination.getUniteAdminDeNomination(), nomination.getAgentNomme());
			postDeResponsabilite = postDao.save(postDeResponsabilite);
			nomination.getUniteAdminDeNomination().getPostesDeResponsabilites().add(postDeResponsabilite);
		}
		
		uniteAdminDao.save(nomination.getUniteAdminDeNomination());
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
