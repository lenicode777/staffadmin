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
import dmp.staffadmin.metier.enumeration.UniteAdminEnum;
import dmp.staffadmin.metier.services.interfaces.IPostMetier;
import dmp.staffadmin.metier.services.interfaces.IUniteAdminConfigService;
import dmp.staffadmin.security.userdetailsservice.IRoleDao;
import dmp.staffadmin.security.userdetailsservice.IUserDao;
import dmp.staffadmin.security.userdetailsservice.IUserMetier;
import dmp.staffadmin.security.userdetailsservice.Role;
import dmp.staffadmin.security.userdetailsservice.User;

@Service @Transactional
public class PostMetier implements IPostMetier 
{
	@Autowired private IAgentDao agentDao;
	@Autowired private IPostDao postDao;
	@Autowired private IUserMetier userMetier;
	@Autowired private IUserDao userDao;
	@Autowired private IRoleDao roleDao;
	@Autowired private IUniteAdminDao uniteAdminDao;
	@Autowired private IUniteAdminConfigService uniteAdminConfigService;

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
		System.out.println("7.1============Poste Metier===========");
		Fonction fonction = post.getFonction(); // Je recupère la fonction de nomination
		UniteAdmin uniteAdmin = post.getUniteAdmin();//Je récupère l'unité admin
		System.out.println("===================PostMetier nommerResponsable Ligne 70 +-=============New UniteAdmin = "+ uniteAdmin);
		Role roleResponsable = roleDao.findByRole(RoleEnum.RESPONSABLE.toString());//Je récupère le role responsable
		
		System.out.println("7.2============Récupération fonction, post, role ok===========");
		String titreNomination = Nomination.getTitreNomination2(fonction, uniteAdmin);
		agent.setTitre(titreNomination);//Je génère le titre de la nomination
		System.out.println("7.3============Titre généré ok===========" + agent.getTitre());
		
		
		agent.setPost(post);//Je met le post  en question sur l'agent
		agent.setTutelleDirecte(uniteAdmin.getIdUniteAdmin()!=DGMP.getIdUniteAdmin() ? uniteAdmin : CabDGMP );//Je défini la tutelleDirecte de l'agent
		agent.setAttenteAffectation(false);
		agent = agentDao.save(agent);//J'enregistre l'agent
		
		System.out.println("7.3.1============Agent Enregistré===========");
		User user = agent.getUser();//Je donne les rôles au user de l'agent
		System.out.println("7.3.2============Avant ajout des roles===========" + agent.getTitre());
		user = userMetier.addRoleToUser(user, fonction.getRoleAssocie());

		System.out.println("ROLE RESPONSABLE = " + roleResponsable.getIdRole() + " " + roleResponsable.getRole());
		user = userMetier.addRoleToUser(user, roleResponsable);
		
		//Si l'uniteAdmin est le SMGP, on lui octroie le role SAF
		if(SMGP.getIdUniteAdmin() == uniteAdmin.getIdUniteAdmin())
		{
			user = userMetier.addRoleToUser(user, roleDao.findByRole(RoleEnum.SAF.toString()));
		}
		
		post.setAgent(agent);//Je met l'agent au post en question
		post.setLibellePost(titreNomination);
		System.out.println("7.4============¨Presque fini ok===========");
		return postDao.save(post);
	}

	@Override
	public Post demettreResponsable(Post post, Agent agent) //******Rem
	{
		
		System.out.println("==========================PostMetier demettreResponsable===========================");
		Fonction fonction = post.getFonction();//Je recupère la fonction de nomination
		Role roleResponsable = roleDao.findById(9L).get();//Je récupère le role responsable
		System.out.println("roleSAf toString = " + RoleEnum.SAF.toString());
		Role roleSAF = roleDao.findByRole(RoleEnum.SAF.toString());
		UniteAdmin DGMP = uniteAdminConfigService.getUniteAdminMere();//Je récupère la DGMP
		System.out.println("Fonction = " + fonction.getIdFonction() + " NomFonction = " + fonction.getNomFonction() + " Roles = "+ fonction.getRoleAssocie());
		System.out.println("RolesRespo = "+ roleResponsable.toString());
		System.out.println("RolesSAF = "+ roleSAF.toString());
		System.out.println("AGENT  = "+agent.toString());
		
		// TODO Remettre l'agent à sa fonction d'origine (=========Algorithme à déterminer========)
		
		agent.setTitre(null); //J'annule le titre
		agent.setPost(null);//J'annule le post de l'agent
		agent.setTutelleDirecte(DGMP);//Je met l'agent directement au niveau de la DGMP
		agent.setAttenteAffectation(true);
		agent = agentDao.save(agent);//J'enregistre les modifications sur l'agent
		
		User user = agent.getUser();//Je récupère le user de l'agent et je lui enlève tous les roles sau
		user = userMetier.removeRoleToUser(user, fonction.getRoleAssocie());
		user = userMetier.removeRoleToUser(user, roleResponsable);
		user = userMetier.removeRoleToUser(user, roleSAF);
		
		post.setAgent(null);
		
		return postDao.save(post);
	}

}
