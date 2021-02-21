package dmp.staffadmin.controllers;

import java.util.List;

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
		Nomination nomination = new Nomination();
		nomination.setUniteAdminDeNomination(uniteAdmin);
		//Agent agentANommer = agentDao.findById(idAgent).get();
		//nomination.setAgentNomme(agentANommer);
		model.addAttribute("nomination", nomination);
		//model.addAttribute("agentANommer", new Agent());
		model.addAttribute("fonctionsNomination", fonctionsNomination);
		
		return "nomination-promotion/nomination-unite-admin";
	}
	
	@PostMapping(path = "/staffadmin/nominations/save")
	public String saveNomination(Model model, @ModelAttribute Nomination nomination)
	{
		/*nomination.setFonctionNomination(fonctionDao.getOne(nomination.getFonctionNomination().getIdFonction()));
		nomination.setUniteAdminDeNomination(uniteAdminDao.getOne(nomination.getUniteAdminDeNomination().getIdUniteAdmin()));
		nomination.setAgentNomme(agentDao.getOne(nomination.getAgentNomme().getIdAgent()));
		nomination.getAgentNomme().setTutelleDirecte(nomination.getUniteAdminDeNomination());
		agentDao.save(nomination.getAgentNomme());
		System.out.println(nomination);
		//Post postManager = new Post();
		if(nomination.getFonctionNomination().isFonctionTopManager())
		{
			Post postManager = new Post(null, nomination.getFonctionNomination(), nomination.getTitreNomination(), nomination.getUniteAdminDeNomination(), nomination.getAgentNomme());
			postManager = postDao.save(postManager);
			nomination.getUniteAdminDeNomination().setPostManager(postManager);
		}
		else
		{
			Post postDeResponsabilite = new Post(null, nomination.getFonctionNomination(), nomination.getTitreNomination(), nomination.getUniteAdminDeNomination(), nomination.getAgentNomme());
			postDeResponsabilite = postDao.save(postDeResponsabilite);
			nomination.getUniteAdminDeNomination().getPostesDeResponsabilites().add(postDeResponsabilite);
		}*/
		
		//uniteAdminDao.save(nomination.getUniteAdminDeNomination());
		//nominationDao.save(nomination);
		nomninationMetier.save(nomination);
		return "redirect:/";
	}
}
