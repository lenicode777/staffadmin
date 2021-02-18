package dmp.staffadmin.controllers;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dmp.staffadmin.dao.ITypeUniteAdminDao;
import dmp.staffadmin.dao.IUniteAdminDao;
import dmp.staffadmin.metier.entities.TypeUniteAdmin;
import dmp.staffadmin.metier.entities.UniteAdmin;
import dmp.staffadmin.metier.interfaces.IUniteAdminMetier;

@Controller
public class UniteAdminController 
{
	@Autowired private IUniteAdminDao uniteAdminDao;
	@Autowired private IUniteAdminMetier uniteAdminMetier;
	@Autowired private ITypeUniteAdminDao typeUniteAdminDao;
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
	
	@GetMapping(path = "/staffadmin/update/{idUniteAdmin}")
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
	
	@PostMapping(path = "/staffadmin/update")
	public String updateUniteAdmin(Model model, @ModelAttribute UniteAdmin uniteAdmin)
	{
		uniteAdminMetier.save(uniteAdmin);
		return "redirect:/staffadmin/unites-admins";
	}
}
