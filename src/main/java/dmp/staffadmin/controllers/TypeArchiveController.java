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
import org.springframework.web.bind.annotation.RequestParam;

import dmp.staffadmin.dao.ITypeArchiveDao;
import dmp.staffadmin.metier.entities.TypeArchive;
import dmp.staffadmin.metier.enumeration.ErrorMsgEnum;
import dmp.staffadmin.metier.exceptions.TypeArchiveException;
import dmp.staffadmin.metier.services.interfaces.ITypeArchiveMetier;
import dmp.staffadmin.security.aspects.AuthoritiesDtoAnnotation;

@Controller
public class TypeArchiveController
{
	@Autowired
	private ITypeArchiveDao typeArchiveDao;
	@Autowired
	private ITypeArchiveMetier typeArchiveMetier;

	@GetMapping(path = "/staffadmin/administration/creation/forms")
	@AuthoritiesDtoAnnotation
	public String goToCreationForms(Model model, HttpServletRequest request, @RequestParam(defaultValue = "new") String mode,
			@RequestParam(defaultValue = "0") Long idTypeArchive)
	{
		goToTypeArchiveForm(model, request, "new", 0L);
		return "administration/creation/frm-creation";
	}

	@GetMapping(path = "/staffadmin/administration/creation/typeArchive/form")
	@AuthoritiesDtoAnnotation
	public String goToTypeArchiveForm(Model model, HttpServletRequest request, @RequestParam(defaultValue = "new") String mode,
			@RequestParam(defaultValue = "0") Long idTypeArchive)
	{
		TypeArchive typeArchive = new TypeArchive();
		if (idTypeArchive != 0)
		{
			typeArchive = typeArchiveDao.findById(idTypeArchive).get();
		}
		List<TypeArchive> listTypeArchive = typeArchiveDao.findAll();

		List<TypeArchive> typesArchive = typeArchiveDao.findAll();

		model.addAttribute("typesArchive", typesArchive);
		model.addAttribute("mode", mode);
		model.addAttribute("typeArchive", typeArchive);
		model.addAttribute("listTypeArchive", listTypeArchive);
		return "administration/creation/frm-creation";
	}

	@PostMapping(path = "/staffadmin/administration/creation/typeArchive/save")
	public String saveNewTypeArchiveForm(Model model, @ModelAttribute TypeArchive typeArchive)
	{
		try
		{
			typeArchiveMetier.save(typeArchive);
			return "redirect:/staffadmin/administration/creation/forms";
		} catch (TypeArchiveException e)
		{
			List<TypeArchive> listTypeArchive = typeArchiveDao.findAll();
			model.addAttribute(ErrorMsgEnum.TYPE_ARCHIVE_ERROR_MSG.toString(), e.getMessage());
			model.addAttribute("listTypeArchive", listTypeArchive);
			return "administration/creation/frm-creation";
		}
	}

	@GetMapping(path = "/staffadmin/administration/creation/typeArchive/delete/{idTypeArchive}")
	public String deleteTypeArchive(@PathVariable Long idTypeArchive)
	{
		typeArchiveDao.deleteById(idTypeArchive);
		return "redirect:/staffadmin/administration/creation/forms";
	}
}
