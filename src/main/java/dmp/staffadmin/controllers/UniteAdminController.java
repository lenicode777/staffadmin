package dmp.staffadmin.controllers;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dmp.staffadmin.dao.IAgentDao;
import dmp.staffadmin.dao.IFonctionDao;
import dmp.staffadmin.dao.ITypeUniteAdminDao;
import dmp.staffadmin.dao.IUniteAdminDao;
import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.metier.entities.Fonction;
import dmp.staffadmin.metier.entities.Nomination;
import dmp.staffadmin.metier.entities.TypeUniteAdmin;
import dmp.staffadmin.metier.entities.UniteAdmin;
import dmp.staffadmin.metier.interfaces.IUniteAdminMetier;
import dmp.staffadmin.security.userdetailsservice.IUserDao;
import dmp.staffadmin.security.userdetailsservice.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Controller
public class UniteAdminController 
{
	@Autowired private IUniteAdminDao uniteAdminDao;
	@Autowired private IUniteAdminMetier uniteAdminMetier;
	@Autowired private ITypeUniteAdminDao typeUniteAdminDao;
	@Autowired private IUserDao userDao;
	@Autowired private IFonctionDao fonctionDao;
	@Autowired private IAgentDao agentDao;
	@GetMapping(path = "/staffadmin/unites-admins")
	public String goToUniteAdmin(Model model)
	{
		//System.out.println("===================================CONTROLLER==========================0");
		
		List<UniteAdmin> scesRattaches = uniteAdminDao.findByTypeUniteAdminNomTypeUniteAdmin("SERVICE RATTACHE");
		List<UniteAdmin> directions = uniteAdminDao.findByTypeUniteAdminNomTypeUniteAdmin("DIRECTION CENTRALE");
		List<UniteAdmin> directionsRegionales = uniteAdminDao.findByTypeUniteAdminNomTypeUniteAdmin("DIRECTION REGIONALE");
		List<TypeUniteAdmin> typesUniteAdmins = typeUniteAdminDao.findByAdministrativeLevelGreaterThan(1);
		
		model.addAttribute("scesRattaches", scesRattaches);
		model.addAttribute("directions", directions);
		model.addAttribute("directionsRegionales", directionsRegionales);
		model.addAttribute("uniteAdmin", new UniteAdmin());
		
		model.addAttribute("typesUniteAdmins", typesUniteAdmins);
		
		return "unite-admin/unite-admin";
	}
	
	@PostMapping(path = "/staffadmin/unite-admins/save")
	public String saveUniteAdmin(Model model, UniteAdmin uniteAdmin)
	{
		//uniteAdmin.getSecretariat().setIdUniteAdmin(null);
		System.out.println("ID="+uniteAdmin.getIdUniteAdmin());
		uniteAdminMetier.save(uniteAdmin);
		return "redirect:/staffadmin/unites-admins";
	}	
	
	@GetMapping(path = "/staffadmin/unites-admins/update/{idUniteAdmin}")
	public String goToFrmUpdateUniteAdmin(Model model, @PathVariable(name = "idUniteAdmin") Long idUniteAdmin)
	{
		List<TypeUniteAdmin> typesUniteAdmins = typeUniteAdminDao.findByAdministrativeLevelGreaterThan(1);
		
		UniteAdmin uniteAdmin = uniteAdminDao.findById(idUniteAdmin).get();
		List<UniteAdmin> unitesAdmins = uniteAdminDao.findByTypeUniteAdminAdministrativeLevelLessThan(uniteAdmin.getTypeUniteAdmin().getAdministrativeLevel());
		model.addAttribute("typesUniteAdmins", typesUniteAdmins);
		model.addAttribute("uniteAdmin", uniteAdmin);
		model.addAttribute("unitesAdmins", unitesAdmins);
		
		return "unite-admin/frm-update-unite-admin";
	}
	
	@PostMapping(path = "/staffadmin/unites-admins/update")
	public String updateUniteAdmin(Model model, @ModelAttribute UniteAdmin uniteAdmin)
	{
		uniteAdminMetier.save(uniteAdmin);
		return "redirect:/staffadmin/unites-admins/"+uniteAdmin.getIdUniteAdmin();
	}
	
	@GetMapping(path="/staffadmin/unites-admins/{idUniteAdmin}")
	public String goToSingleUniteAdmin(Model model, HttpServletRequest request, @PathVariable Long idUniteAdmin)
	{
		User authUser = userDao.findByUsername(request.getUserPrincipal().getName());
		UniteAdmin visitedUniteAdmin = uniteAdminDao.findById(idUniteAdmin).get();
		List<Long> idResponsablesTutelles = null;
		
		List<String> roles = authUser.getRoles().stream().map(r->r.getRole()).collect(Collectors.toList());
		if(visitedUniteAdmin.getPostManager()!=null)
		{
			idResponsablesTutelles = visitedUniteAdmin.getPatrentsStream().map(ua->ua.getPostManager().getAgent().getIdAgent()).collect(Collectors.toList());
		}
		
		
		if(!roles.contains("SAF"))
		{
			if(idResponsablesTutelles==null)
			{
				throw new RuntimeException("Accès réfusé");
			}
			else if(!roles.contains("RESPONSABLE"))
			{
				throw new RuntimeException("Accès réfusé");
			}
			else
			{
				if(!idResponsablesTutelles.contains(authUser.getAgent().getIdAgent()))
				{
					throw new RuntimeException("Accès réfusé");
				}
			}
		}
		
		List<UniteAdmin> unitesAdminsSousTutelles = visitedUniteAdmin.getSubAdminStream().map(ua->ua.getUniteAdminSousTutelle()).flatMap(listUa->listUa.stream()).collect(Collectors.toList());
		List<Agent> personnel = visitedUniteAdmin.getSubAdminStream().map(UniteAdmin::getPersonnel).flatMap(listAgents->listAgents.stream()).collect(Collectors.toList());
		model.addAttribute("visitedUniteAdmin", visitedUniteAdmin);
		model.addAttribute("unitesAdminsSousTutelles", unitesAdminsSousTutelles);
		model.addAttribute("personnel", personnel);
		
		UniteAdminStats uniteAdminStats = new UniteAdminStats();
		uniteAdminStats.setNbAgents(uniteAdminMetier.getNbAgents(visitedUniteAdmin));
		uniteAdminStats.setNbGarcons(uniteAdminMetier.getNbGarcons(visitedUniteAdmin));
		uniteAdminStats.setNbFilles(uniteAdminMetier.getNbFilles(visitedUniteAdmin));
		//uniteAdminStats.setNbFonctionnaires(uniteAdminMetier.getNbFonctionnaires(visitedUniteAdmin));
		//uniteAdminStats.setNbContractuelles(uniteAdminMetier.getNbContractuelles(visitedUniteAdmin));
		
		model.addAttribute("uniteAdminStats", uniteAdminStats);
		
		return "unite-admin/single/single-unite-admin";
	}
	
}

@Data @NoArgsConstructor @AllArgsConstructor
class UniteAdminStats
{
	private UniteAdmin uniteAdmin;
	private long nbAgents;
	private long nbGarcons;
	private long nbFilles;
	private long nbUnitesAdminsSoututelles;
	private long nbContractuelles;
	private long nbFonctionnaires;
}
