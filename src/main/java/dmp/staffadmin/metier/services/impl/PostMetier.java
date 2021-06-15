package dmp.staffadmin.metier.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dmp.staffadmin.dao.IAgentDao;
import dmp.staffadmin.dao.IPostDao;
import dmp.staffadmin.dao.IUniteAdminDao;
import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.metier.entities.Fonction;
import dmp.staffadmin.metier.entities.Nomination;
import dmp.staffadmin.metier.entities.Post;
import dmp.staffadmin.metier.entities.UniteAdmin;
import dmp.staffadmin.metier.enumeration.RoleEnum;
import dmp.staffadmin.metier.services.interfaces.IPostMetier;
import dmp.staffadmin.metier.services.interfaces.IUniteAdminConfigService;
import dmp.staffadmin.security.dao.AppRoleDao;
import dmp.staffadmin.security.dao.AppUserDao;
import dmp.staffadmin.security.model.AppRole;
import dmp.staffadmin.security.model.AppUser;
import dmp.staffadmin.security.services.IUserMetier;

@Service
@Transactional
public class PostMetier implements IPostMetier
{
	@Autowired
	private IAgentDao agentDao;
	@Autowired
	private IPostDao postDao;
	@Autowired
	private IUserMetier userMetier;
	@Autowired
	private AppUserDao userDao;
	@Autowired
	private AppRoleDao roleDao;
	@Autowired
	private IUniteAdminDao uniteAdminDao;
	@Autowired
	private IUniteAdminConfigService uniteAdminConfigService;

	@Override
	public Post save(Post e)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Post update(Post e)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Post update(Long entityId, Post entityBody)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Post> findAll()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Post nommerResponsable(Post post, Agent agent)
	{
		UniteAdmin SMGP = uniteAdminConfigService.getDRH();
		UniteAdmin DGMP = uniteAdminConfigService.getUniteAdminMere();
		UniteAdmin CabDGMP = uniteAdminConfigService.getCabinetUniteAdminMere();
		
		Fonction fonction = post.getFonction();
		UniteAdmin uniteAdmin = post.getUniteAdmin();
		String titreNomination = Nomination.getTitreNomination2(fonction, uniteAdmin);
		
		agent.setFonctionNomination(fonction);
		agent.setTutelleDirecte(uniteAdmin.getIdUniteAdmin() != DGMP.getIdUniteAdmin() ? uniteAdmin : CabDGMP);
		agent.setTitre(titreNomination);
		agent.setPost(post);															
		agent.setAttenteAffectation(false);
		agent = agentDao.save(agent);
		
		AppRole roleResponsable = roleDao.findByRoleName(RoleEnum.RESPONSABLE.toString());
		AppUser user = agent.getUser();
		user = userMetier.addRoleToUser(user, fonction.getRoleAssocie());
		user = userMetier.addRoleToUser(user, roleResponsable);
		if (SMGP.getIdUniteAdmin() == uniteAdmin.getIdUniteAdmin())
		{
			user = userMetier.addRoleToUser(user, roleDao.findByRoleName(RoleEnum.DRH.toString()));
		}

		post.setAgent(agent);
		post.setLibellePost(titreNomination);
		return postDao.save(post);
	}

	@Override
	public Post demettreResponsable(Post post, Agent agent) // ******Rem
	{
		Fonction fonction = post.getFonction();// Je recupère la fonction de nomination
		AppRole roleResponsable = roleDao.findByRoleSigle(RoleEnum.RESPONSABLE.toString());// Je récupère le role responsable
		UniteAdmin DGMP = uniteAdminConfigService.getUniteAdminMere();// Je récupère la DGMP

		agent.setFonctionNomination(null);
		agent.setTitre(null); // J'annule le titre
		agent.setPost(null);// J'annule le post de l'agent
		agent.setTutelleDirecte(DGMP);// Je met l'agent directement au niveau de la DGMP
		agent.setAttenteAffectation(true);
		agent = agentDao.save(agent);// J'enregistre les modifications sur l'agent

		AppUser user = agent.getUser();// Je récupère le user de l'agent et je lui enlève tous les roles sau
		user = userMetier.removeRoleToUser(user, fonction.getRoleAssocie());
		user = userMetier.removeRoleToUser(user, roleResponsable);
		user.setIdUaChampVisuel(null);
		userDao.save(user);

		post.setAgent(null);
		return postDao.save(post);
	}

}
