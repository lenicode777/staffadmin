package dmp.staffadmin.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import dmp.staffadmin.dao.IAgentDao;
import dmp.staffadmin.dao.IFonctionDao;
import dmp.staffadmin.dao.INominationDao;
import dmp.staffadmin.dao.IPostDao;
import dmp.staffadmin.dao.ITypeUniteAdminDao;
import dmp.staffadmin.dao.IUniteAdminDao;
import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.metier.entities.Fonction;
import dmp.staffadmin.metier.entities.Nomination;
import dmp.staffadmin.metier.entities.Post;
import dmp.staffadmin.metier.entities.UniteAdmin;
import dmp.staffadmin.metier.interfaces.INominationMetier;
import dmp.staffadmin.security.userdetailsservice.IUserDao;
import dmp.staffadmin.security.userdetailsservice.User;

@Controller
public class NominationController 
{
	@Autowired private INominationMetier nomninationMetier;
	@Autowired private IFonctionDao fonctionDao;
	@Autowired private IAgentDao agentDao;
	@Autowired private IPostDao postDao;
	@Autowired private IUserDao userDao;
	@Autowired private INominationDao nominationDao;
	@Autowired private IUniteAdminDao uniteAdminDao;
	@Autowired private ITypeUniteAdminDao typeUniteAdminDao;
	
	@GetMapping(path = "/staffadmin/nominations/form")
	public String goToNominationForm(Model model, HttpServletRequest request, 
									 @RequestParam(name = "idAgent") Long idAgent)
	{
		User user = userDao.findByUsername(request.getUserPrincipal().getName());
		Nomination nomination = new Nomination();
		Agent agentANommer = agentDao.findById(idAgent).get();
		nomination.setAgentNomme(agentANommer);
		model.addAttribute("nomination", nomination);
		model.addAttribute("agentANommer", agentANommer);
		model.addAttribute("fonctionsNomination", fonctionDao.findByFonctionDeNominationTrue());
		
		return "nomination-promotion/nomination-promotion";
	}
	
	@GetMapping(path = "/staffadmin/{idUniteAdmin}/nominations/form")
	public String goToNominationForm2(Model model, HttpServletRequest request,
									 @PathVariable Long idUniteAdmin)
	{
		
		//User user = userDao.findByUsername(request.getUserPrincipal().getName());
		UniteAdmin uniteAdmin = uniteAdminDao.findById(idUniteAdmin).get();
	
		//TypeUniteAdmin type = uniteAdmin.getTypeUniteAdmin();
		//System.out.println("TypeUniteAdmin = "+type.getNomTypeUniteAdmin() + " ID = "+ type.getIdTypeUniteAdmin() );
		List<Fonction> fonctionsNomination = fonctionDao.findByTypeUniteAdminIdTypeUniteAdmin(uniteAdmin.getTypeUniteAdmin().getIdTypeUniteAdmin());
		List<String> titres = fonctionsNomination.stream().map(f->Nomination.getTitreNomination2(f, uniteAdmin)).collect(Collectors.toList());
		Nomination nomination = new Nomination();
		nomination.setUniteAdminDeNomination(uniteAdmin);
		
		//Agent agentANommer = agentDao.findById(idAgent).get();
		//nomination.setAgentNomme(agentANommer);
		model.addAttribute("nomination", nomination);
		//model.addAttribute("agentANommer", new Agent());
		model.addAttribute("fonctionsNomination", fonctionsNomination);
		model.addAttribute("titres", titres);
		
		return "nomination-promotion/nomination-unite-admin";
	}
	
	@PostMapping(path = "/staffadmin/nominations/save")
	public String saveNomination(Model model, @ModelAttribute Nomination nomination)
	{
		System.out.println("============================NominationController SAVE METHODE=========================");
		System.out.println("titre : "+nomination.getTitreNomination());
		System.out.println("Fonction : "+nomination.getFonctionNomination().
		getIdFonction());
		System.out.println("date : "+nomination.getDateNomination());
		System.out.println("Agent: "+nomination.getAgentNomme().toString()+"ID = "+nomination.getAgentNomme().getIdAgent());
		  
		  
		Agent agentANommer = agentDao.findById(nomination.getAgentNomme().getIdAgent()).get();
		Fonction fonctionDeNomFonction = fonctionDao.findById(nomination.getFonctionNomination().getIdFonction()).get();
		UniteAdmin uniteAdminDeNomination = uniteAdminDao.findById(nomination.getUniteAdminDeNomination().getIdUniteAdmin()).get();
		
		nomination.setAgentNomme(agentANommer);
		nomination.setFonctionNomination(fonctionDeNomFonction);
		nomination.setUniteAdminDeNomination(uniteAdminDeNomination);
		
		nomninationMetier.save(nomination);
		return "redirect:/staffadmin/unites-admins/"+nomination.getUniteAdminDeNomination().getIdUniteAdmin();
	}
	
	@PostMapping(path = "/staffadmin/nomination/confirmation")
	public String goToConfirmationNomination(Model model, HttpServletRequest request, @ModelAttribute Nomination nomination)
	{
		UniteAdmin uniteAdminDeNomination = uniteAdminDao.findById(nomination.getUniteAdminDeNomination().getIdUniteAdmin()).get();
		Fonction fonctionNomination = fonctionDao.findById(nomination.getFonctionNomination().getIdFonction()).get();
		
		//System.out.println("FONCTION1111111 = "+fonctionNomination.getNomFonction());
		//fonctionNomination.setTypeUniteAdmin(typeUniteAdminDao.findById().get());
		Agent agentANommer = agentDao.findByMatricule(nomination.getAgentNomme().getMatricule());
		
		nomination.setAgentNomme(agentANommer);
		nomination.setUniteAdminDeNomination(uniteAdminDeNomination);
		nomination.setFonctionNomination(fonctionNomination);
		model.addAttribute("nomination", nomination);
		
		System.out.println("ID_FONCTION = "+ nomination.getFonctionNomination().getIdFonction());
		System.out.println("titre : "+nomination.getTitreNomination());
		System.out.println("Fonction : "+nomination.getFonctionNomination().getNomFonction());
		System.out.println("date : "+nomination.getDateNomination());
		System.out.println("Agent: "+nomination.getAgentNomme().toString());
		
		return "nomination-promotion/confirmation-nomination-unite-admin";
	}
}
