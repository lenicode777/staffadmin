package dmp.staffadmin.controllers;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ExceptionHandler;
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
		return "redirect:/staffadmin/profil?idAgent="+affectation.getAgent().getIdAgent();
	}
	
	@PreAuthorize("hasRole('SAF')")
	//@PreAuthorize("hasAuthority('ROLE_SAF')")
	@GetMapping(path = "/staffadmin/frm-affectations-groupees/{idUaArrivee}")
	public String goToFrmAffectationGroupee(HttpServletRequest request, Model model, @PathVariable Long idUaArrivee)
	{
		UniteAdmin authUserVisibility=null;
		User authUser = userDao.findByUsername(request.getUserPrincipal().getName()); 
		if(authUser.hasRole(RoleEnum.SAF.toString()))
		{
			authUserVisibility = uniteAdminDao.findBySigle("DGMP");
		}
		else if(authUser.hasRole(RoleEnum.DIRECTEUR.toString()) || authUser.hasRole(RoleEnum.SOUS_DIRECTEUR.toString()) )
		{
			authUserVisibility = authUser.getAgent().getTutelleDirecte();
		}
		else
		{
			throw new AuthorityException("Desolé! Vous ne disposez des droits pour acceder à cette ressource");
		}
		//UniteAdmin DGMP = uniteAdminDao.findBySigle("DGMP");
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
		String mode = "edition";
		AffectationGroupeeForm affectationGroupeeForm = new AffectationGroupeeForm();
		affectationGroupeeForm.setUaArrivee(uaArrivee);
		affectationGroupeeForm.setPossibleDestinations(possibleDestinations);
		
		try
		{
			List<List<Agent>> listOfListagentsAffectables = authUserVisibility.getSubAdminStream()
												.map(UniteAdmin::getPersonnel)
												.collect(Collectors.toList());
			
			List<Agent> agentsAffectables = listOfListagentsAffectables
												.stream()
												.flatMap(List::stream)
												.filter(Agent::isAffectable)
												.collect(Collectors.toList());
											
			affectationGroupeeForm.setListAgentsAffectables(agentsAffectables);
			//model.addAttribute("agentsAffectables", agentsAffectables);
			
			//model.addAttribute("possibleDestinations", possibleDestinations);
			model.addAttribute("affectationGroupeeForm",  affectationGroupeeForm);
			model.addAttribute("mode", mode);
		}
		catch (Exception e)
		{
			//model.addAttribute("affectationGroupeeForm",  null);
			model.addAttribute("mode", mode);
			model.addAttribute("globalErrorMsg", "Aucun agent sous votre tutelle n'est susceptible de faire l'objet d'une affectation");
		}
		return "affectation/affectations-groupees/frm-affectations-groupees";
	}
	
	@PreAuthorize("hasAuthority('SAF')")
	@PostMapping(path = "/staffadmin/confirmation/affectations-groupees")
	public String goToConfirmationFrmAffectationGroupee(HttpServletRequest request, Model model, @ModelAttribute AffectationGroupeeForm affectationGroupeeForm)
	{
		//UniteAdmin DGMP = uniteAdminDao.findBySigle("DGMP");
		UniteAdmin uaDepart = uniteAdminDao.findById(affectationGroupeeForm.getUaDepart().getIdUniteAdmin()).get();
		UniteAdmin uaArrivee = uniteAdminDao.findById(affectationGroupeeForm.getUaArrivee().getIdUniteAdmin()).get();
		
		affectationGroupeeForm.getListIdsAgents().forEach(id->System.out.println("ID = " + id));
		
		List<Agent> listAgentsAAffecter = affectationGroupeeForm.getListIdsAgents().stream().map(id->agentDao.findById(id).get()).collect(Collectors.toList());
		Date dateAffectation = affectationGroupeeForm.getDateAffectation();
		
		
		affectationGroupeeForm.setUaDepart(uaDepart);
		affectationGroupeeForm.setUaDepart(uaDepart);
		affectationGroupeeForm.setDateAffectation(dateAffectation);
		affectationGroupeeForm.setListAgentsAAffecter(listAgentsAAffecter);
		
		
		String mode = "confirmation";

		
		model.addAttribute("affectationGroupeeForm",  affectationGroupeeForm);
		//model.addAttribute("unitesAdmins", uniteAdminDao.findAll());
		model.addAttribute("mode", mode);
		
		return "affectation/affectations-groupees/frm-affectations-groupees";
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
}
