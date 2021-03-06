package dmp.staffadmin.metier.services.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dmp.staffadmin.dao.IAgentDao;
import dmp.staffadmin.dao.IPostDao;
import dmp.staffadmin.dao.ITypeUniteAdminDao;
import dmp.staffadmin.dao.IUniteAdminDao;
import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.metier.entities.Fonction;
import dmp.staffadmin.metier.entities.Nomination;
import dmp.staffadmin.metier.entities.Post;
import dmp.staffadmin.metier.entities.UniteAdmin;
import dmp.staffadmin.metier.services.interfaces.IPostMetier;
import dmp.staffadmin.metier.services.interfaces.IUniteAdminMetier;
import dmp.staffadmin.metier.validation.IUniteAdminValidation;

@Service
@Transactional
public class UniteAdaminMetier implements IUniteAdminMetier
{
	@Autowired
	private IUniteAdminDao uniteAdminDao;
	@Autowired
	private IUniteAdminValidation uniteAdminValidation;
	@Autowired
	private IAgentDao agentDao;
	@Autowired
	private IPostMetier postMetier;
	@Autowired
	private IPostDao postDao;
	@Autowired
	private ITypeUniteAdminDao typeUniteAdminDao;

	@Override
	public UniteAdmin save(UniteAdmin uniteAdmin)
	{
		uniteAdminValidation.validate(uniteAdmin);

		// TypeUniteAdmin typeUniteAdmin =
		// typeUniteAdminDao.findById(uniteAdmin.getTypeUniteAdmin().getIdTypeUniteAdmin()).get();
		// uniteAdmin.setTypeUniteAdmin(typeUniteAdmin);

		UniteAdmin tutelleDirecte = uniteAdminDao.findById(uniteAdmin.getTutelleDirecte().getIdUniteAdmin()).get();
		uniteAdmin.setTutelleDirecte(tutelleDirecte);
		tutelleDirecte.ajouterUA(uniteAdmin);

		uniteAdminValidation.validate(uniteAdmin);
		Post postOfManager = new Post(null, null, null, uniteAdmin, null);

		postOfManager.setUniteAdmin(uniteAdmin);
		postOfManager = postDao.save(postOfManager);

		uniteAdmin.setPostManager(postOfManager);
		return uniteAdminDao.save(uniteAdmin);
	}

	@Override
	public UniteAdmin update(UniteAdmin uniteAdmin)
	{
		UniteAdmin uaFromDb = uniteAdminDao.findById(uniteAdmin.getIdUniteAdmin()).get();
		uaFromDb.setAppellation(uniteAdmin.getAppellation());
		uaFromDb.setSigle(uniteAdmin.getSigle());
		uaFromDb.setContacts(uniteAdmin.getContacts());
		uaFromDb.setTypeUniteAdmin(uniteAdmin.getTypeUniteAdmin());
		uaFromDb.setDateCreation(uniteAdmin.getDateCreation());
		uaFromDb.setTutelleDirecte(uniteAdmin.getTutelleDirecte());
		uaFromDb.setSituationGeo(uniteAdmin.getSituationGeo());
		uniteAdminValidation.validate(uaFromDb);
		System.out.println("===Modif Réussi===");
		return uniteAdminDao.save(uaFromDb);
	}

	@Override
	public UniteAdmin update(Long uniteAdminId, UniteAdmin uniteAdminBody)
	{
		uniteAdminBody.setIdUniteAdmin(uniteAdminId);
		return this.update(uniteAdminBody);
	}

	@Override
	public List<UniteAdmin> findAll()
	{
		return null;
	}

	@Override
	public UniteAdmin setSubAdminTree(UniteAdmin uniteAdmin)
	{
		List<UniteAdmin> subAdmins = uniteAdminDao.findByTutelleDirecte(uniteAdmin);
		uniteAdmin.setUniteAdminSousTutelle(subAdmins);
		subAdmins.forEach(subAdmin ->
		{
			setSubAdminTree(subAdmin);
		});
		return uniteAdmin;
	}

	@Override
	public List<Agent> getAllPersonnel(UniteAdmin uniteAdmin)
	{
		List<Agent> allPersonnel = uniteAdmin.getSubAdminStream().map(ua -> ua.getPersonnel())
				.flatMap(Collection::stream).collect(Collectors.toList());
		return allPersonnel;
	}

	@Override
	public long getNbAgents(UniteAdmin uniteAdmin)
	{
		return uniteAdmin.getSubAdminStream().map(UniteAdmin::getPersonnel).flatMap(listAgent -> listAgent.stream())
				.count();
	}

	@Override
	public long getNbGarcons(UniteAdmin uniteAdmin)
	{
		return uniteAdmin.getSubAdminStream().map(UniteAdmin::getPersonnel).flatMap(listAgent -> listAgent.stream())
				.filter(agent -> agent.getSexe().equalsIgnoreCase("M") || agent.getSexe().equalsIgnoreCase("HOMME"))
				.count();
	}

	@Override
	public long getNbFilles(UniteAdmin uniteAdmin)
	{
		return uniteAdmin.getSubAdminStream().map(UniteAdmin::getPersonnel).flatMap(listAgent -> listAgent.stream())
				.filter(agent -> agent.getSexe().equalsIgnoreCase("F") || agent.getSexe().equalsIgnoreCase("FEMME"))
				.count();
	}

	@Override
	public long getNbFonctionnaires(UniteAdmin uniteAdmin)
	{
		return uniteAdmin.getSubAdminStream().map(UniteAdmin::getPersonnel).flatMap(listAgent -> listAgent.stream())
				.filter(agent -> agent.getStatutAgent().equalsIgnoreCase("FONCTIONNAIRE")).count();
	}

	@Override
	public long getNbContractuelles(UniteAdmin uniteAdmin)
	{
		return uniteAdmin.getSubAdminStream().map(UniteAdmin::getPersonnel).flatMap(listAgent -> listAgent.stream())
				.filter(agent -> agent.getSexe().equalsIgnoreCase("CONTRACTUEL")).count();
	}

	private UniteAdmin nommerManager(UniteAdmin uniteAdmin, Agent agentANommer, Fonction fonctionDeNomination)
	{
		agentANommer = agentDao.findById(agentANommer.getIdAgent()).get();
		uniteAdmin = uniteAdminDao.findById(uniteAdmin.getIdUniteAdmin()).get();

		
		Post postOfManager = uniteAdmin.getPostManager();

		if (postOfManager == null)
		{
			postOfManager = new Post(null, fonctionDeNomination,
					Nomination.getTitreNomination2(fonctionDeNomination, uniteAdmin), uniteAdmin, agentANommer);
			postOfManager = postDao.save(postOfManager);
			uniteAdmin.setPostManager(postOfManager);
			uniteAdmin = uniteAdminDao.save(uniteAdmin);
		} else
		{
			Agent ancienManager = postOfManager.getAgent();
			if (ancienManager != null)
			{
				postMetier.demettreResponsable(postOfManager, ancienManager);
			}
			postOfManager.setFonction(fonctionDeNomination);// J'initialise la fonction de nomination pour le Post
		}

		Post ancienPostDeAgentANommer = agentANommer.getPost();

		if (ancienPostDeAgentANommer != null)
		{
			if (ancienPostDeAgentANommer.getFonction().isFonctionDeNomination())
			{
				postMetier.demettreResponsable(ancienPostDeAgentANommer, agentANommer);
			}
		}
		postMetier.nommerResponsable(postOfManager, agentANommer);

		return uniteAdmin;
	}

	private UniteAdmin nommerAutreResponsable(UniteAdmin uniteAdminDeNomination, Agent agentANommer,
			Fonction fonctionDeNomination)
	{
		Optional<Post> postResponsable = Optional.empty(); // On recherche un éventuel post de responsabilité vide pour
															// la même fonction au sein de L'UA de nomination
		try
		{
			Long idFonction = fonctionDeNomination.getIdFonction(); // Je capte l'identifiant de la fonction de
																	// nomination pour le filtre à la ligne suivante
			postResponsable = uniteAdminDeNomination.getPostesDeResponsabilites().stream()
					.filter(p -> p.getAgent() == null && p.getFonction().getIdFonction() == idFonction).findFirst();
			System.out.println("4============Passe le filtre des postes de respo avec succes===========");
		} catch (Exception e) // Juste par précaution
		{
			System.out.println(
					"5===============================PRECAUTION CATCH RESPONSABILITE POST==========================");
			System.out.println(e.getMessage());
		}

		Post postDeNomination = new Post();

		if (!postResponsable.isPresent()) // Si ce post n'existe pas, on le crée
		{
			postDeNomination = new Post(null, fonctionDeNomination,
					Nomination.getTitreNomination2(fonctionDeNomination, uniteAdminDeNomination),
					uniteAdminDeNomination, agentANommer);
			postDeNomination.setUniteAdmin(uniteAdminDeNomination);
																	
			postDeNomination.setAgent(agentANommer);
			postDeNomination = postDao.save(postDeNomination);
		} 
		else
		{
			postDeNomination = postResponsable.get(); // On le récupère
			postDeNomination.setUniteAdmin(uniteAdminDeNomination);// On lui défini l'UA de nomination comme unite admin
			postDeNomination.setAgent(agentANommer); // On met l'agentANommer à ce post
			postDeNomination = postDao.save(postDeNomination); // On l'enregistre

		}
		postDeNomination = postMetier.nommerResponsable(postDeNomination, agentANommer);
		return postDeNomination.getUniteAdmin();
	}

	@Override
	public UniteAdmin nommerResponsable(UniteAdmin uniteAdmin, Agent agentANommer, Fonction fonctionDeNomination)
	{
		if (fonctionDeNomination.isFonctionTopManager())
			return nommerManager(uniteAdmin, agentANommer, fonctionDeNomination);
		else
			return nommerAutreResponsable(uniteAdmin, agentANommer, fonctionDeNomination);

	}
}
