package dmp.staffadmin.controllers;

import dmp.staffadmin.dao.IAgentDao;
import dmp.staffadmin.dao.IDepartDao;
import dmp.staffadmin.dao.ITypeDepartDao;
import dmp.staffadmin.metier.constants.DateFormatConstants;
import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.metier.entities.Depart;
import dmp.staffadmin.metier.entities.TypeDepart;
import dmp.staffadmin.metier.enumeration.TypeDepartEnum;
import dmp.staffadmin.metier.exceptions.DepartException;
import dmp.staffadmin.metier.services.interfaces.IDepartMetier;
import dmp.staffadmin.security.aspects.AuthoritiesDtoAnnotation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

@Controller
public class DepartController 
{
	private final IAgentDao agentDao;
	private final ITypeDepartDao typeDepartDao;
	private final IDepartMetier departMetier;
	private final IDepartDao departDao;

	public DepartController(IAgentDao agentDao, ITypeDepartDao typeDepartDao, IDepartMetier departMetier, IDepartDao departDao)
	{
		this.agentDao = agentDao;
		this.typeDepartDao = typeDepartDao;
		this.departMetier = departMetier;
		this.departDao = departDao;
	}

	@GetMapping(path = "/staffadmin/frm-nouveau-depart")
	@AuthoritiesDtoAnnotation	
	public String goToFrmNouveauDepart(Model model, HttpServletRequest request, 
			@RequestParam(defaultValue = "") String matricule,
			@RequestParam(defaultValue = "") String motif, 
			@RequestParam(defaultValue = "0") long idTypeDepart,
			@RequestParam(defaultValue = "") String dateDepart) throws ParseException
	{
		Agent agent = null;
		TypeDepart typeDepart = null;
		if(!matricule.equals("") && agentDao.existsByMatricule(matricule))
		{
			agent = agentDao.findByMatricule(matricule);
		}
		else if(!matricule.equals("") && !agentDao.existsByMatricule(matricule))
		{
			model.addAttribute("errorMsg", "Matricule introuvable");
		}
		if(idTypeDepart!=0 && typeDepartDao.existsById(idTypeDepart))
		{
			typeDepart = typeDepartDao.findById(idTypeDepart).get();
		}
		
		Depart depart = new Depart();
		depart.setAgent(agent);
		depart.setMotif(motif);
		depart.setTypeDepart(typeDepart);
		
		if (!dateDepart.equals(""))
		{
			try
			{
				SimpleDateFormat sdf = new SimpleDateFormat(DateFormatConstants.DATE_FORMAT_FROM_HTML_INPUT, Locale.US);
				depart.setDateDepart(sdf.parse(dateDepart));
			}
			catch (Exception e) 
			{}
		}
		
		model.addAttribute("depart",depart);
		model.addAttribute("typeDeparts", typeDepartDao.findAll());
		return "depart/departs";
	}
	
	@PostMapping(path = "/staffadmin/departs/confirmation")
	@AuthoritiesDtoAnnotation
	public String goToConfirmationDepart(Model model, HttpServletRequest request, @ModelAttribute Depart depart)
	{
		depart.setTypeDepart(typeDepartDao.findById(depart.getTypeDepart().getId()).get());
		model.addAttribute("depart", depart);
		return "depart/form/frm-confirmation-depart";
	}
	
	@PostMapping(path = "/staffadmin/departs/save")
	@AuthoritiesDtoAnnotation
	public String enregistrerNouveauDepart(Model model, HttpServletRequest request, @ModelAttribute Depart depart)
	{
		try
		{
			departMetier.save(depart);
		}
		catch (DepartException e)
		{
			model.addAttribute("errorMsg", e.getMessage());
			return "depart/departs";
		}
		
		model.addAttribute("depart", depart);
		return "redirect:/staffadmin/frm-nouveau-depart";
	}
	
	@GetMapping(path = "/staffadmin/list-departs")
	@AuthoritiesDtoAnnotation
	public String goToListeDeparts(Model model, HttpServletRequest request,
								   @RequestParam(defaultValue = "") String nomTypeDepart)
	{
		if(nomTypeDepart=="" || !typeDepartDao.existsByNomType(nomTypeDepart))
		{
			model.addAttribute("departs",departDao.findAll());
		}
		else
		{
			TypeDepart typeDepart = typeDepartDao.findByNomType(nomTypeDepart);
			model.addAttribute("departs",departDao.findByTypeDepart(typeDepart));
			model.addAttribute("titreTableau", typeDepart.getNomType() + "s");
			if(typeDepart.getNomType().equals(TypeDepartEnum.DECES.toString()))
			{
				model.addAttribute("titreTableau", "Nécrologie");
			}
		}
		List<TypeDepart> typeDeparts = typeDepartDao.findAll();
		model.addAttribute("typeDeparts", typeDeparts);
		return "depart/departs.html";
	}
	
	@GetMapping(path = "/staffadmin/departs")
	@AuthoritiesDtoAnnotation
	public String goToDeparts(Model model, HttpServletRequest request,
							  @RequestParam(defaultValue = "") String matricule,
							  @RequestParam(defaultValue = "") String motif,
							  @RequestParam(defaultValue = "0") long idTypeDepart,
							  @RequestParam(defaultValue = "") String dateDepart,
							  @RequestParam(defaultValue = "") String nomTypeDepart) throws ParseException {
		goToListeDeparts(model,request,nomTypeDepart);
		goToFrmNouveauDepart(model, request, matricule, motif, idTypeDepart, dateDepart);
		
		return"depart/departs";
	}

}
