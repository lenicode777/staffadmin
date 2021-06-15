package dmp.staffadmin.metier.services.impl;

import dmp.staffadmin.controllers.dto.factories.IArchivageAgentFactory;
import dmp.staffadmin.dao.IAgentDao;
import dmp.staffadmin.dao.IDepartDao;
import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.metier.entities.ArchiveAgent;
import dmp.staffadmin.metier.entities.Depart;
import dmp.staffadmin.metier.enumeration.PositionEnum;
import dmp.staffadmin.metier.enumeration.SituationPresenceEnum;
import dmp.staffadmin.metier.services.interfaces.IArchivageAgentMetier;
import dmp.staffadmin.metier.services.interfaces.IDepartMetier;
import dmp.staffadmin.metier.services.interfaces.IPostMetier;
import dmp.staffadmin.metier.validation.IDepartValidation;
import dmp.staffadmin.security.dao.AppUserDao;
import dmp.staffadmin.security.model.AppUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Transactional(noRollbackFor = {Exception.class, RuntimeException.class})
public class DepartMetier implements IDepartMetier 
{
	private final IDepartValidation departValidation;
	private final IDepartDao departDao;
	private final IAgentDao agentDao;
	private final IPostMetier postMetier;
	private final AppUserDao userDao;
	private final IArchivageAgentFactory archivageAgentFactory;
	private final IArchivageAgentMetier archivageAgentMetier;

	public DepartMetier(IDepartValidation departValidation, IDepartDao departDao, IAgentDao agentDao, IPostMetier postMetier, AppUserDao userDao, IArchivageAgentFactory archivageAgentFactory, IArchivageAgentMetier archivageAgentMetier)
	{
		this.departValidation = departValidation;
		this.departDao = departDao;
		this.agentDao = agentDao;
		this.postMetier = postMetier;
		this.userDao = userDao;
		this.archivageAgentFactory = archivageAgentFactory;
		this.archivageAgentMetier = archivageAgentMetier;
	}

	@Override
	public Depart save(Depart depart) 
	{
		departValidation.validate(depart);
		Agent agent = agentDao.findById(depart.getAgent().getIdAgent()).get();
		AppUser user = agent.getUser();
		if(agent.getPost()!=null)
		{
			postMetier.demettreResponsable(agent.getPost(), agent);
		}
		else
		{
			agent.setTitre(null); // J'annule le titre
			agent.setFonctionNomination(null);
			agent = agentDao.save(agent);

			user.setIdUaChampVisuel(null);
			userDao.save(user);
		}
		agent.setTutelleDirecte(null);
		agent.setActive(false);
		agent.setAttenteAffectation(false);
		agent.setSituationPresence(SituationPresenceEnum.PARTI.toString());
		agent.setPosition(PositionEnum.PARTI.toString());
		agentDao.save(agent);

		ArchiveAgent archiveAgent = archivageAgentFactory.createArchivageAgentFromDepart(depart);
		archiveAgent = archivageAgentMetier.uploadArchiveAgent(archiveAgent);
		depart.setArchiveAgent(archiveAgent);
		return departDao.save(depart);
	}
	
}
