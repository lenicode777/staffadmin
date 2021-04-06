package dmp.staffadmin.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dmp.staffadmin.dao.ITypeArchiveDao;
import dmp.staffadmin.metier.entities.TypeArchive;
import dmp.staffadmin.metier.enumeration.ErrorMsgEnum;
import dmp.staffadmin.metier.services.interfaces.ITypeArchiveMetier;

@Controller
public class TypeArchiveController
{
	@Autowired
	private ITypeArchiveDao typeArchiveDao;
	@Autowired
	private ITypeArchiveMetier typeArchiveMetier;

	@GetMapping(path = "/staffadmin/administration/creation/forms")
	public String goToCreationForms(Model model, @RequestParam(defaultValue = "new") String mode,
			@RequestParam(defaultValue = "0") Long idTypeArchive)
	{
		goToTypeArchiveForm(model, "new", 0L);
		return "administration/creation/frm-creation";
	}

	@GetMapping(path = "/staffadmin/administration/creation/typeArchive/form")
	public String goToTypeArchiveForm(Model model, @RequestParam(defaultValue = "new") String mode,
			@RequestParam(defaultValue = "0") Long idTypeArchive)
	{
		TypeArchive typeArchive = new TypeArchive();
		if (idTypeArchive != 0)
		{
			typeArchive = typeArchiveDao.findById(idTypeArchive).get();
		}
		List<TypeArchive> listTypeArchive = typeArchiveDao.findAll();

		model.addAttribute("mode", mode);
		model.addAttribute("typeArchive", typeArchive);
		model.addAttribute("listTypeArchive", listTypeArchive);
		return "administration/creation/frm-creation";
	}

	@PostMapping(path = "/staffadmin/administration/creation/typeArchive/save")
	public String saveNewTypeArchiveForm(Model model, @ModelAttribute TypeArchive typeArchive)
	{
		if (typeArchiveDao.existsByNomIgnoreCase(typeArchive.getNom()))
		{
			model.addAttribute(ErrorMsgEnum.ERROR_MSG.toString(), "Type d'archive existant!");
			return "administration/creation/frm-creation";
		} else
		{
			typeArchiveMetier.save(typeArchive);
			return "redirect:/staffadmin/administration/creation/forms";
		}
	}
}
