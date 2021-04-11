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
import dmp.staffadmin.metier.services.interfaces.IAgentMetier;
import dmp.staffadmin.metier.services.interfaces.IUniteAdminConfigService;
import dmp.staffadmin.metier.validation.IAgentValidation;
import dmp.staffadmin.security.userdetailsservice.IRoleDao;
import dmp.staffadmin.security.userdetailsservice.IUserDao;
import dmp.staffadmin.security.userdetailsservice.IUserMetier;
import dmp.staffadmin.security.userdetailsservice.Role;
import dmp.staffadmin.security.userdetailsservice.User;

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
	private IUserDao userDao;
	@Autowired
	private IUserMetier userMetier;
	@Autowired
	private IRoleDao roleDao;
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
		agent = agentDao.save(agent);

		return agent;
	}

	@Override
	public Recrutement recruter(Agent agent)
	{
		Recrutement recrutement = new Recrutement();
		// save(agent);
		User user = new User();
		user.setAgent(agent);
		user.setActive(true);
		user.setUsername(agent.getNom() + "_" + agent.getMatricule());
		user.setPassword(agent.getMatricule());

		Role roleAgent = roleDao.findByRole("AGENT");
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
		Agent agentFromDataBAse = agentDao.findById(agentId).get();

		agentFromDataBAse.setNom(agentForm.getNom());
		agentFromDataBAse.setPrenom(agentForm.getPrenom());
		agentFromDataBAse.setDateNaissance(agentForm.getDateNaissance());
		agentFromDataBAse.setSexe(agentForm.getSexe());
		agentFromDataBAse.setEmail(agentForm.getEmail());
		agentFromDataBAse.setLieuNaissance(agentForm.getLieuNaissance());
		agentFromDataBAse.setDepartementNaissance(agentForm.getDepartementNaissance());
		agentFromDataBAse.setFixeBureau(agentForm.getFixeBureau());
		agentFromDataBAse.setTel(agentForm.getTel());

		agentFromDataBAse.setTypePiece(agentForm.getTypePiece());
		agentFromDataBAse.setNumPiece(agentForm.getNumPiece());
		agentFromDataBAse.setNomPere(agentForm.getNomPere());
		agentFromDataBAse.setNomMere(agentForm.getNomMere());

		agentFromDataBAse.setFonction(agentForm.getFonction());
		agentFromDataBAse.setEmploi(agentForm.getEmploi());
		agentFromDataBAse.setMatricule(agentForm.getMatricule());
		agentFromDataBAse.setDatePriseService1(agentForm.getDatePriseService1());
		agentFromDataBAse.setGrade(agentForm.getGrade());
		agentFromDataBAse.setStatutAgent(agentForm.getStatutAgent());

		agentFromDataBAse.setDatePriseServiceDGMP(agentForm.getDatePriseServiceDGMP());
		agentFromDataBAse.setNumBadge(agentForm.getNumBadge());
		agentFromDataBAse.setEmailPro(agentForm.getEmailPro());

		// agentBody.setIdAgent(agentId);

		return save(agentFromDataBAse);
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
