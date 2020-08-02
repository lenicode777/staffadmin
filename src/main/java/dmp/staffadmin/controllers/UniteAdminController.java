package dmp.staffadmin.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	@GetMapping(path = "/sataffadmin/respo/unites-admins")
	public String goToUniteAdmin(Model model)
	{
		List<UniteAdmin> unitesAdmins = uniteAdminDao.findByLevelGreaterThan(2);
		List<UniteAdmin> scesRattaches = uniteAdminDao.findByTypeUniteAdmin(typeUniteAdminDao.findByNomTypeUniteAdmin("SERVICE RATTACHE"));
		List<UniteAdmin> sds = uniteAdminDao.findByTypeUniteAdmin(typeUniteAdminDao.findByNomTypeUniteAdmin("SOUS DIRECTION"));
		List<UniteAdmin> drs = uniteAdminDao.findByTypeUniteAdmin(typeUniteAdminDao.findByNomTypeUniteAdmin("DIRECTION REGIONALE"));
		List<TypeUniteAdmin> typesUniteAdmins = typeUniteAdminDao.findByAdministrativeLevelGreaterThan(2);
		UniteAdmin DGBF = uniteAdminDao.findBySigle("DGBF").get(0);
		DGBF = uniteAdminMetier.setSubAdminTree(DGBF);
		UniteAdmin DMP = DGBF.getUniteAdminSousTutelle().get(0);
		model.addAttribute("dmp", DMP);
		UniteAdmin uniteAdmin = new UniteAdmin();
		uniteAdmin.setTypeUniteAdmin(new TypeUniteAdmin());
		uniteAdmin.setTutelleDirecte(new UniteAdmin());
		model.addAttribute("uniteAdmin", new UniteAdmin());

		model.addAttribute("typesUniteAdmins", typesUniteAdmins);
		model.addAttribute("unitesAdmins", unitesAdmins);
		model.addAttribute("scesRattaches", scesRattaches);
		model.addAttribute("sds", sds);
		model.addAttribute("drs", drs);
		return "unite-admin/unite-admin";
	}
	
	@PostMapping(path = "/staffadmin/saf/unite-admin/save")
	public String saveUniteAdmin(Model model, UniteAdmin uniteAdmin)
	{
		uniteAdminMetier.save(uniteAdmin);
		return "redirect:/sataffadmin/respo/unites-admins";
	}
}
