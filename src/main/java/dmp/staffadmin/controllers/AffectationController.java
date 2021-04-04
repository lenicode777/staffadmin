package dmp.staffadmin.controllers;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import dmp.staffadmin.dao.IAffectationDao;
import dmp.staffadmin.dao.IAgentDao;
import dmp.staffadmin.dao.IUniteAdminDao;
import dmp.staffadmin.metier.entities.Affectation;
import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.metier.entities.UniteAdmin;
import dmp.staffadmin.metier.enumeration.RoleEnum;
import dmp.staffadmin.metier.exceptions.AffectationException;
import dmp.staffadmin.metier.exceptions.AuthorityException;
import dmp.staffadmin.metier.interfaces.IAffectationMetier;
import dmp.staffadmin.metier.interfaces.IUniteAdminConfigService;
import dmp.staffadmin.security.userdetailsservice.IUserDao;
import dmp.staffadmin.security.userdetailsservice.IUserMetier;
import dmp.staffadmin.security.userdetailsservice.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Controller
public class AffectationController 
{
	@Autowired private IAgentDao agentDao;
	@Autowired private IAffectationDao affectationDao;
	@Autowired private IUniteAdminDao uniteAdminDao;
	@Autowired private IAffectationMetier affectationMetier;
	@Autowired private IUserDao userDao;
	@Autowired private IUserMetier userMetier;
	@Autowired private IUniteAdminConfigService uniteAdminConfigService;
	
	//@PreAuthorize("hasAuthority('SAF')")
	@GetMapping(path = "/staffadmin/affectations/{idAgent}")
	public String goToFrmSingleAffectation(Model model, HttpServletRequest request, @PathVariable Long idAgent)
	{
		String mode = "edition";
		Affectation affectation = new Affectation();
		Agent agentAAffecter = agentDao.findById(idAgent).get();
		affectation.setAgent(agentAAffecter);
		List<UniteAdmin> unitesAdmins = uniteAdminDao.findAll();
		model.addAttribute("affectation", affectation);
		model.addAttribute("unitesAdmins", unitesAdmins);
		model.addAttribute("mode", mode);
		return "affectation/frm-affectation";
	}
	
	//@PreAuthorize("hasAuthority('SAF')")
	@PostMapping(path = "/staffadmin/affectations/confirmation")
	public String goToConfirmationSingleAffectation(Model model, HttpServletRequest request, @ModelAttribute Affectation affectation)
	{
		String mode = "confirmation";
		Agent agentAAffecter = agentDao.findById(affectation.getAgent().getIdAgent()).get();
		UniteAdmin uaArrivee = uniteAdminDao.findById(affectation.getUaArrivee().getIdUniteAdmin()).get();
		UniteAdmin uaDepart = agentAAffecter.getTutelleDirecte();
		
		affectation.setAgent(agentAAffecter);
		affectation.setUaArrivee(uaArrivee);
		affectation.setUaDepart(uaDepart);
		model.addAttribute("affectation", affectation);
		model.addAttribute("mode", mode);
		return "affectation/frm-affectation";
	}
	

	
	@PostMapping(path = "/staffadmin/affectations/save")
	public String saveAffectation(Model model, HttpServletRequest request, @ModelAttribute Affectation affectation)
	{
		try
		{
			affectationMetier.save(affectation);
		}
		catch (Exception e) 
		{
			if(e instanceof AffectationException)
			{
				model.addAttribute("affectation", affectation);
				model.addAttribute("mode", "confirmation");
				model.addAttribute("globalErrorMsg", e.getMessage());
				return "affectation/frm-affectation";
			}
			else
			{
				System.out.println(e.getMessage());
			}
		}
		return "redirect:/staffadmin/profil?idAgent="+affectation.getAgent().getIdAgent()+"&successMsg=Affectation effectuée avec succès!";
	}
	
	@PreAuthorize("hasRole('SAF') or hasRole('D') or hasRole('SD')")
	//@PreAuthorize("hasAuthority('ROLE_SAF')")
	@GetMapping(path = "/staffadmin/frm-affectations-groupees/{idUaArrivee}")
	public String goToFrmAffectationGroupee(HttpServletRequest request, Model model, @PathVariable Long idUaArrivee)
	{
		String mode = "edition";
		try
		{
			UniteAdmin authUserVisibility=null;
			User authUser = userDao.findByUsername(request.getUserPrincipal().getName()); 
			if(authUser.hasRole(RoleEnum.SAF.toString()))
			{
				authUserVisibility = uniteAdminConfigService.getUniteAdminMere();
			}
			else if(authUser.hasRole(RoleEnum.DIRECTEUR.toString()) || authUser.hasRole(RoleEnum.SOUS_DIRECTEUR.toString()) )
			{
				authUserVisibility = authUser.getAgent().getTutelleDirecte();
			}
			else
			{
				throw new AuthorityException("Desolé! Vous ne disposez des droits pour acceder à cette ressource");
			}
			
			UniteAdmin uaArrivee = null;
			List<UniteAdmin> possibleDestinations = null;
			if(idUaArrivee!=0)
			{
				uaArrivee = uniteAdminDao.findById(idUaArrivee).get();
				possibleDestinations = null;
			}
			else
			{
				possibleDestinations = authUserVisibility.getSubAdminStream().collect(Collectors.toList());
				uaArrivee = null;
			}
			
			AffectationGroupeeForm affectationGroupeeForm = new AffectationGroupeeForm();
			affectationGroupeeForm.setAuthUserVisibilityId(authUserVisibility.getIdUniteAdmin());
			affectationGroupeeForm.setUaArrivee(uaArrivee);
			affectationGroupeeForm.setPossibleDestinations(possibleDestinations);
		
		
			List<List<Agent>> listOfListagentsAffectables = authUserVisibility.getSubAdminStream()
												.map(UniteAdmin::getPersonnel)
												.collect(Collectors.toList());
			
			List<Agent> agentsAffectables = listOfListagentsAffectables
												.stream()
												.flatMap(List::stream)
												.filter(Agent::isAffectable)
												.collect(Collectors.toList());
											
			affectationGroupeeForm.setListAgentsAffectables(agentsAffectables);
			model.addAttribute("affectationGroupeeForm",  affectationGroupeeForm);
			model.addAttribute("mode", mode);
		}
		catch (Exception e)
		{
			model.addAttribute("mode", mode);
			model.addAttribute("globalErrorMsg", "Aucun agent sous votre tutelle n'est susceptible de faire l'objet d'une affectation");
		}
		return "affectation/affectations-groupees/frm-affectations-groupees";
	}
	
	@PreAuthorize("hasRole('SAF') or hasRole('D') or hasRole('SD')")
	@PostMapping(path = "/staffadmin/confirmation/affectations-groupees")
	public String goToConfirmationFrmAffectationGroupee(HttpServletRequest request, Model model, @ModelAttribute AffectationGroupeeForm affectationGroupeeForm)
	{
		UniteAdmin uaArrivee = uniteAdminDao.findById(affectationGroupeeForm.getUaArrivee().getIdUniteAdmin()).get();
		
		affectationGroupeeForm.getListIdsAgents().forEach(id->System.out.println("ID = " + id));
		
		List<Agent> listAgentsAAffecter = affectationGroupeeForm.getListIdsAgents().stream().map(id->agentDao.findById(id).get()).collect(Collectors.toList());

		affectationGroupeeForm.setListAgentsAAffecter(listAgentsAAffecter);
		affectationGroupeeForm.setUaArrivee(uaArrivee);
		
		try
		{
			if(affectationGroupeeForm.getListIdsAgents().size()<1)
			{
				throw new AffectationException("Veuillez choisir les agents à affecter");
			}
		}
		catch (AffectationException e)
		{
			System.out.println("affectationGroupeeForm.getAuthUserVisibilityId() = " + affectationGroupeeForm.getAuthUserVisibilityId());
			UniteAdmin authUserVisibility = uniteAdminDao.findById(affectationGroupeeForm.getAuthUserVisibilityId()).get();
			List<UniteAdmin> possibleDestinations = authUserVisibility.getSubAdminStream().collect(Collectors.toList());
			
			List<List<Agent>> listOfListagentsAffectables = authUserVisibility.getSubAdminStream()
					.map(UniteAdmin::getPersonnel)
					.collect(Collectors.toList());

			List<Agent> agentsAffectables = listOfListagentsAffectables
					.stream()
					.flatMap(List::stream)
					.filter(Agent::isAffectable)
					
					.collect(Collectors.toList());
			
			affectationGroupeeForm.setPossibleDestinations(possibleDestinations);
			affectationGroupeeForm.setListAgentsAffectables(agentsAffectables);
			
			model.addAttribute("globalErrorMsg", e.getMessage());
			model.addAttribute("mode", "edition");
			model.addAttribute("affectationGroupeeForm",  affectationGroupeeForm);
			return "affectation/affectations-groupees/frm-affectations-groupees";
		}
		String mode = "confirmation";

		model.addAttribute("affectationGroupeeForm",  affectationGroupeeForm);
		model.addAttribute("mode", mode);
		
		return "affectation/affectations-groupees/frm-affectations-groupees";
	}
	
	@PreAuthorize("hasRole('SAF') or hasRole('D') or hasRole('SD')")
	@PostMapping(path="/staffadmin/affectations-groupees/save")
	public String saveAffectationsGroupees(HttpServletRequest request, Model model, @ModelAttribute AffectationGroupeeForm affectationGroupeeForm)
	{
		UniteAdmin uaArrivee = affectationGroupeeForm.getUaArrivee();
		List<Agent> agentsAAffecter = affectationGroupeeForm.getListIdsAgents().stream()
									 .map(id->agentDao.findById(id).get())
									 .collect(Collectors.toList());
		Date dateAffectation = affectationGroupeeForm.getDateAffectation();
		Affectation affectation = new Affectation();
		try
		{
			for(Long idAgent:affectationGroupeeForm.getListIdsAgents())
			{
				Agent agent = new Agent(); //Je crée un agent 
				agent.setIdAgent(idAgent); //Je lui passe seulement son identifiant (La fonction save de affectationMetier va récupérer toutes les infos de l'agent à partir de l'id)
				affectation = new Affectation(); // je crée une nouvelle affectation
				affectation.setAgent(agent);// Je lui passe l'agent
				affectation.setUaArrivee(uaArrivee);//Je lui passe ua de destination
				affectation.setDateAffectation(dateAffectation);//Je lui passe la date de l'affectation
				affectationMetier.save(affectation);//La fonction save de affectationMetier fera le reste
			}
		}
		catch(AffectationException e)
		{
			if(e instanceof AffectationException)
			{
				model.addAttribute("affectation", affectation);
				model.addAttribute("mode", "confirmation");
				model.addAttribute("globalErrorMsg", e.getMessage());
				return "affectation/frm-affectation";
			}
			else
			{
				System.out.println(e.getMessage());
			}
		}
		
		affectationGroupeeForm.getListIdsAgents().forEach(id->System.out.println("ID = "+id));
		
		System.out.println("Date affectation = " + affectationGroupeeForm.getDateAffectation());
	
		return "redirect:/staffadmin/unites-admins/"+uaArrivee.getIdUniteAdmin()+ "?successMsg="+affectationGroupeeForm.getListIdsAgents().size()+ " Affectation(s) effectuée(s) avec succès!";
	}
}

@Data @NoArgsConstructor @AllArgsConstructor
class AffectationGroupeeForm
{
	private UniteAdmin uaDepart;
	private UniteAdmin uaArrivee;
	private List<UniteAdmin> possibleDestinations;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateAffectation;
	private List<Long> listIdsAgents;
	private List<Agent> listAgentsAAffecter;
	private List<Agent> listAgentsAffectables;
	private Long authUserVisibilityId; //ID de l'unite admin du authUser
}
