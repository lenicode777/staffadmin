package dmp.staffadmin.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;
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
import dmp.staffadmin.metier.enumeration.TypeUniteAdminEnum;
import dmp.staffadmin.metier.exceptions.AuthorityException;
import dmp.staffadmin.metier.exceptions.UniteAdminException;
import dmp.staffadmin.metier.interfaces.IUniteAdminMetier;
import dmp.staffadmin.metier.validation.IUniteAdminValidation;
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
	@Autowired private IUniteAdminValidation uaValidation;
	@GetMapping(path = "/staffadmin/unites-admins")
	public String goToUniteAdmin(Model model)
	{
		//System.out.println("===================================CONTROLLER==========================0");
		UniteAdmin DGMP = uniteAdminDao.findBySigle("DGMP");
		UniteAdmin cabinetDGMP = uniteAdminDao.findByTypeUniteAdminNomTypeUniteAdmin(TypeUniteAdminEnum.CABINET_DG.toString()).get(0);
		System.out.println("Cabinet DGMP = " + cabinetDGMP);
		List<UniteAdmin> scesRattaches = uniteAdminDao.findByLevelAndTypeUniteAdminNomTypeUniteAdmin(DGMP.getLevel()+1 ,TypeUniteAdminEnum.SERVICE.toString());
		List<UniteAdmin> directions = uniteAdminDao.findByTypeUniteAdminNomTypeUniteAdmin("DIRECTION CENTRALE");
		List<UniteAdmin> directionsRegionales = uniteAdminDao.findByTypeUniteAdminNomTypeUniteAdmin("DIRECTION REGIONALE");
		List<TypeUniteAdmin> typesUniteAdmins = typeUniteAdminDao.findByAdministrativeLevelGreaterThan(1);
		
		model.addAttribute("cabinetDGMP", cabinetDGMP);
		model.addAttribute("scesRattaches", scesRattaches);
		model.addAttribute("directions", directions);
		model.addAttribute("directionsRegionales", directionsRegionales);
		model.addAttribute("uniteAdmin", new UniteAdmin());
		
		model.addAttribute("typesUniteAdmins", typesUniteAdmins);
		
		return "unite-admin/unite-admin";
	}
	@PostMapping(path = "/staffadmin/frm-new-unite-admin/confirmation")
	public String goToConfirmationNewUniteAdmin(Model model, UniteAdmin uniteAdmin)
	{
		
		return "";
	}
	@PostMapping(path = "/staffadmin/unite-admins/save")
	public String saveUniteAdmin(Model model, UniteAdmin uniteAdmin)
	{
		//uniteAdmin.getSecretariat().setIdUniteAdmin(null);
		System.out.println("ID="+uniteAdmin.getIdUniteAdmin());
		uniteAdminMetier.save(uniteAdmin);
		return "redirect:/staffadmin/unites-admins";
	}	
	
	@PreAuthorize("hasRole('SAF')")
	@GetMapping(path = "/staffadmin/unites-admins/frm/{idUniteAdmin}") // Path changé update->frm --Faire un refactoring
	public String goToFrmUniteAdmin(HttpServletRequest request, Model model, @PathVariable(name = "idUniteAdmin") Long idUniteAdmin)
	{
		List<TypeUniteAdmin> typesUniteAdmins = typeUniteAdminDao.findByAdministrativeLevelGreaterThan(1);
		UniteAdmin uniteAdmin = new UniteAdmin();

		model.addAttribute("typesUniteAdmins", typesUniteAdmins);
				
		if(idUniteAdmin==0) // Si l'identifiant fournit dans le path == 0 alors on fait un forward vers le formulaire new UniteAdmin
		{
			model.addAttribute("uniteAdmin", uniteAdmin);
			return "unite-admin/frm-new-unite-admin";
		}
		else // Si l'identifiant fournit dans le path != 0 alors on fait un forward vers le formulaire update UniteAdmin
		{
			try
			{
				uniteAdmin = uniteAdminDao.findById(idUniteAdmin).get();
				model.addAttribute("uniteAdmin", uniteAdmin);
				List<UniteAdmin> unitesAdmins = uniteAdminDao.findByTypeUniteAdminAdministrativeLevelLessThan(uniteAdmin.getTypeUniteAdmin().getAdministrativeLevel());
				model.addAttribute("unitesAdmins", unitesAdmins);
				return "unite-admin/frm-update-unite-admin";
			}
			catch(NoSuchElementException e)
			{
				model.addAttribute("globalErrorMsg", "Impossible de charger les données de l'unité administrative");
			
				return "unite-admin/frm-update-unite-admin";
			}
		}
	}
	
	@PostMapping(path = "/staffadmin/unite-admin/frm-confirmation-new")
	public String goToFrmConfirmation(HttpServletRequest request, Model model, 
									  @ModelAttribute UniteAdmin uniteAdmin)
	{
		try
		{
			uaValidation.validate(uniteAdmin);
			uaValidation.validateNoneExistence(uniteAdmin);
			model.addAttribute("uniteAdmin", uniteAdmin);
		}
		catch (UniteAdminException e) 
		{
			model.addAttribute("globalErrorMsg", e.getMessage());
		}
		
		return "unite-admin/frm-confirmation-new-unite-admin";
	}
	
	@PostMapping(path = "/staffadmin/unites-admins/update")
	public String updateUniteAdmin(Model model, @ModelAttribute UniteAdmin uniteAdmin)
	{		
		uniteAdminMetier.update(uniteAdmin);
		return "redirect:/staffadmin/unites-admins/"+uniteAdmin.getIdUniteAdmin();
	}
	
	@GetMapping(path="/staffadmin/unites-admins/{idUniteAdmin}")
	public String goToSingleUniteAdmin(Model model, HttpServletRequest request, @PathVariable Long idUniteAdmin)
	{
		User authUser = userDao.findByUsername(request.getUserPrincipal().getName());
		UniteAdmin visitedUniteAdmin = uniteAdminDao.findById(idUniteAdmin).get();
		List<Long> idResponsablesTutelles = new ArrayList<>();
		
		List<String> roles = authUser.getRoles().stream().map(r->r.getRole()).collect(Collectors.toList());
		if(visitedUniteAdmin.getPostManager()!=null)
		{
			List<Optional<Long>> optionalIdResponsablesTutelles = visitedUniteAdmin
									.getPatrentsStream().filter(ua->ua.getPostManager()!=null ).peek(ua->System.out.println(ua.getAppellation()))
									.filter(ua->ua.getPostManager().getAgent()!=null)
									.map(ua->Optional.ofNullable(ua.getPostManager()
									.getAgent().getIdAgent())).collect(Collectors.toList());
			
			for(Optional<Long> ol: optionalIdResponsablesTutelles)
			{
				ol.ifPresent(l->idResponsablesTutelles.add(l));
			}
									
		}
		
		
		if(!roles.contains("SAF"))
		{
			if(idResponsablesTutelles==null || idResponsablesTutelles.size()==0)
			{
				throw new AuthorityException("Accès réfusé");
			}
			else if(!roles.contains("RESPONSABLE"))
			{
				throw new AuthorityException("Accès réfusé");
			}
			else
			{
				if(!idResponsablesTutelles.contains(authUser.getAgent().getIdAgent()))
				{
					throw new AuthorityException("Accès réfusé");
				}
			}
		}
		
		UniteAdmin DGMP = uniteAdminDao.findBySigle("DGMP");
		UniteAdmin cabinetDGMP = uniteAdminDao.findByTypeUniteAdminNomTypeUniteAdmin(TypeUniteAdminEnum.CABINET_DG.toString()).get(0);
		List<UniteAdmin> unitesAdminsSousTutelles = visitedUniteAdmin.getSubAdminStream().map(ua->ua.getUniteAdminSousTutelle()).flatMap(listUa->listUa.stream()).collect(Collectors.toList());
		List<Agent> personnel = visitedUniteAdmin.getSubAdminStream().map(UniteAdmin::getPersonnel).flatMap(listAgents->listAgents.stream()).collect(Collectors.toList());
		
		model.addAttribute("DGMP", DGMP);
		model.addAttribute("cabinetDGMP", cabinetDGMP);
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
