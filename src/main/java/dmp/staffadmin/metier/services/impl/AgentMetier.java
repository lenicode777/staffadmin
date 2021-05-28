package dmp.staffadmin.metier.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dmp.staffadmin.dao.IAgentDao;
import dmp.staffadmin.dao.IRecrutementDao;
import dmp.staffadmin.dao.IUniteAdminDao;
import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.metier.entities.Recrutement;
import dmp.staffadmin.metier.entities.UniteAdmin;
import dmp.staffadmin.metier.enumeration.EtatRecrutement;
import dmp.staffadmin.metier.enumeration.RoleEnum;
import dmp.staffadmin.metier.services.interfaces.IAgentMetier;
import dmp.staffadmin.metier.services.interfaces.IUniteAdminConfigService;
import dmp.staffadmin.metier.validation.IAgentValidation;
import dmp.staffadmin.security.dao.AppRoleDao;
import dmp.staffadmin.security.dao.AppUserDao;
import dmp.staffadmin.security.model.AppRole;
import dmp.staffadmin.security.model.AppUser;
import dmp.staffadmin.security.services.IUserMetier;

@Service
@Transactional
public class AgentMetier implements IAgentMetier
{
	@Autowired
	private IUniteAdminDao uniteAdminDao;
	@Autowired
	private IAgentDao agentDao;
	@Autowired
	private IAgentValidation agentValidation;
	@Autowired
	private IRecrutementDao recrutementDao;
	@Autowired
	private AppUserDao userDao;
	@Autowired
	private IUserMetier userMetier;
	@Autowired
	private AppRoleDao roleDao;
	@Autowired
	private IUniteAdminConfigService uniteAdminConfigService;

	@Override
	public boolean existingEmail(String email)
	{
		return !agentDao.findByEmail(email).isEmpty();
	}

	@Override
	public boolean existingEmail(String email, Long idAgent)
	{
		return (!agentDao.existsByEmail(email) ? false
				: agentDao.findByEmail(email).get(0).getIdAgent() == idAgent ? false : true);
	}

	@Override
	public boolean existingTel(String tel)
	{
		return agentDao.existsByTel(tel);
	}

	@Override
	public boolean existingNumPiece(String numPiece)
	{
		return !agentDao.findByNumPiece(numPiece).isEmpty();
	}

	@Override
	public boolean existingMatricule(String matricule)
	{
		return agentDao.existsByMatricule(matricule);
	}

	@Override
	public boolean existingTel(String tel, Long idAgent)
	{
		return (!agentDao.existsByTel(tel) ? false
				: agentDao.findByTel(tel).get(0).getIdAgent() == idAgent ? false : true);
	}

	@Override
	public boolean existingNumPiece(String numPiece, Long idAgent)
	{
		return (!agentDao.existsByNumPiece(numPiece) ? false
				: agentDao.findByNumPiece(numPiece).get(0).getIdAgent() == idAgent ? false : true);
	}

	@Override
	public boolean existingMatricule(String matricule, Long idAgent)
	{
		return (!agentDao.existsByMatricule(matricule) ? false
				: agentDao.findByMatricule(matricule).getIdAgent() == idAgent ? false : true);
	}

	@Override
	public boolean existingEmailPro(String emailPro)
	{
		return agentDao.existsByEmailPro(emailPro);
	}

	@Override
	public boolean existingEmailPro(String emailPro, Long idAgent)
	{
		return (!agentDao.existsByEmailPro(emailPro) ? false
				: agentDao.findByEmailPro(emailPro).getIdAgent() == idAgent ? false : true);
	}

	@Override
	public boolean existingNumBadge(String numBadge)
	{
		return agentDao.existsByNumBadge(numBadge);
	}

	@Override
	public boolean existingNumBadge(String numBadge, Long idAgent)
	{
		return (!agentDao.existsByNumBadge(numBadge) ? false
				: agentDao.findByNumBadge(numBadge).getIdAgent() == idAgent ? false : true);
	}

	@Override
	public Agent save(Agent agent)
	{
		agentValidation.validate(agent);
		agent.setActive(false);
		agent.setAttenteAffectation(true);
		UniteAdmin DGMP = uniteAdminConfigService.getUniteAdminMere();
		agent.setTutelleDirecte(DGMP);
		if (agent.getSexe().contains("F") || agent.getSexe().contains("f"))
		{
			agent.setNomPhoto("inconnue.png");
		} else
		{
			agent.setNomPhoto("inconnu.jpg");
		}
		if(agent.getNumBadge()!=null)
		{
			if(agent.getNumBadge().equals(""))
			{
				agent.setNumBadge(null);
			}
		}
		if(agent.getEmailPro()!=null)
		{
			if(agent.getEmailPro().equals(""))
			{
				agent.setEmailPro(null);
			}
		}
		agent = agentDao.save(agent);

		return agent;
	}

	@Override
	public Recrutement recruter(Agent agent)
	{
		Recrutement recrutement = new Recrutement();
		// save(agent);
		AppUser user = new AppUser();
		user.setAgent(agent);
		user.setActive(true);
		user.setUsername(agent.getNom() + "_" + agent.getMatricule());
		user.setPassword(agent.getMatricule());

		AppRole roleAgent = roleDao.findByRoleName(RoleEnum.AGENT.toString());
		user.addRole(roleAgent);

		user = userMetier.save(user);

		agent.setUser(user);

		agent = save(agent);

		recrutement.setAgent(agent);
		recrutement.setDateEnregistrementAgent(new Date());
		recrutement.setStatut(EtatRecrutement.ATTENTE_MUTATION.toString());
		return recrutementDao.save(recrutement);
	}

	@Override
	public Agent update(Agent agent)
	{
		return save(agent);
	}

	@Override
	public Agent update(Long agentId, Agent agentForm)
	{
		Agent agentFromDataBase = agentDao.findById(agentId).get();
		
		agentFromDataBase.setNom(agentForm.getNom());
		agentFromDataBase.setPrenom(agentForm.getPrenom());
		agentFromDataBase.setDateNaissance(agentForm.getDateNaissance());
		agentFromDataBase.setSexe(agentForm.getSexe());
		agentFromDataBase.setEmail(agentForm.getEmail());
		agentFromDataBase.setLieuNaissance(agentForm.getLieuNaissance());
		agentFromDataBase.setDepartementNaissance(agentForm.getDepartementNaissance());
		agentFromDataBase.setFixeBureau(agentForm.getFixeBureau());
		agentFromDataBase.setTel(agentForm.getTel());

		agentFromDataBase.setTypePiece(agentForm.getTypePiece());
		agentFromDataBase.setNumPiece(agentForm.getNumPiece());
		agentFromDataBase.setNomPere(agentForm.getNomPere());
		agentFromDataBase.setNomMere(agentForm.getNomMere());

		agentFromDataBase.setFonction(agentForm.getFonction());
		agentFromDataBase.setEmploi(agentForm.getEmploi());
		agentFromDataBase.setMatricule(agentForm.getMatricule());
		agentFromDataBase.setDatePriseService1(agentForm.getDatePriseService1());
		agentFromDataBase.setGrade(agentForm.getGrade());
		agentFromDataBase.setStatutAgent(agentForm.getStatutAgent());

		agentFromDataBase.setDatePriseServiceDGMP(agentForm.getDatePriseServiceDGMP());
		if(!agentForm.getNumBadge().equals(""))
		{
			agentFromDataBase.setNumBadge(agentForm.getNumBadge());
		}
		if(!agentForm.getEmailPro().equals(""))
		{
			agentFromDataBase.setEmailPro(agentForm.getEmailPro());
		}

		// agentBody.setIdAgent(agentId);

		return save(agentFromDataBase);
	}

	@Override
	public List<Agent> findAll()
	{
		return agentDao.findAll();
	}

	@Override
	public Agent affecter(Agent agent, UniteAdmin UAdestination)
	{
		return null;
	}

	@Override
	public List<Agent> findActive()
	{

		return agentDao.findByActiveTrue();
	}

	@Override
	public List<Agent> findNoneActive()
	{
		return agentDao.findByActiveFalse();
	}

	@Override
	public List<Agent> findByPosition(String position)
	{
		return agentDao.findByPosition(position);
	}

	@Override
	public List<Agent> findBySexe(String sexe)
	{
		return agentDao.findBySexe(sexe);
	}

	@Override
	public List<Agent> findBySituationPresence(String situationPresence)
	{
		return agentDao.findBySituationPresence(situationPresence);
	}

	@Override
	public List<Agent> findByStatutAgent(String statutAgent)
	{
		return agentDao.findByStatutAgent(statutAgent);
	}

	// @Bean
	CommandLineRunner start(IAgentDao agentDao, IUniteAdminDao uaDao)
	{

		return arg ->
		{
			List<List<Agent>> listsAgents = uniteAdminConfigService.getUniteAdminMere().getSubAdminStream()
					.map(UniteAdmin::getPersonnel).collect(Collectors.toList());
			List<Agent> agents = listsAgents.stream().flatMap(List::stream).filter(Agent::isAffectable)
					.collect(Collectors.toList());
			// System.out.println(zie);
			agents.forEach(agent -> System.out.println(agent + " est affectable ? " + agent.isAffectable()));
			// System.out.println("Zie est il affectable ? " + zie.isAffectable());
		};

	}
}
