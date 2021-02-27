package dmp.staffadmin.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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
import dmp.staffadmin.metier.exceptions.AffectationException;
import dmp.staffadmin.metier.interfaces.IAffectationMetier;

@Controller
public class AffectationController 
{
	@Autowired private IAgentDao agentDao;
	@Autowired private IAffectationDao affectationDao;
	@Autowired private IUniteAdminDao uniteAdminDao;
	@Autowired private IAffectationMetier affectationMetier;
	
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
	
	@ExceptionHandler
	public String exceptionHandling(Exception e)
	{
		if(e instanceof AffectationException)
		{
			
		}
		return null;
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
}
