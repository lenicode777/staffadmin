package dmp.staffadmin.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
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
import dmp.staffadmin.metier.enumeration.ModeEnum;
import dmp.staffadmin.metier.exceptions.NominationException;
import dmp.staffadmin.metier.interfaces.INominationMetier;
import dmp.staffadmin.metier.validation.INominationValidation;
import dmp.staffadmin.metier.validation.NominationValidation;
import dmp.staffadmin.security.userdetailsservice.IUserDao;
import dmp.staffadmin.security.userdetailsservice.User;
import dmp.staffadmin.utilities.Constants;

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
	@Autowired private INominationValidation nominationValidation;
	@GetMapping(path = "/staffadmin/nominations/form")
	public String goToNominationForm(Model model, HttpServletRequest request, 
									 @RequestParam(name = "idAgent") Long idAgent,
									 @RequestParam(defaultValue = "0") Long idFonction,
									 @RequestParam(defaultValue = "0") Long idUniteAdmin,
									 @RequestParam(defaultValue = "") String dateNomination)
	{
		User user = userDao.findByUsername(request.getUserPrincipal().getName());
		Nomination nomination = new Nomination();
		Agent agentANommer = null;
		try
		{
			agentANommer = agentDao.findById(idAgent).get();
			System.out.println("Nomination Controller L67==========Agent Existe===============");
			nomination.setAgentNomme(agentANommer);
			
		}
		catch(Exception e)
		{
			if(e instanceof NoSuchElementException)
			{
				model.addAttribute("globalErrorMsg", "Références incorrectes");
			}
		}
		
		if(idFonction!=0)
		{
			if(fonctionDao.existsById(idFonction))
			{
				Fonction fonctionDeNomination  = fonctionDao.findById(idFonction).get();
				//List<UniteAdmin> unitesAdmins = uniteAdminDao.findAll();
				List<UniteAdmin> unitesAdmins = uniteAdminDao.findByTypeUniteAdmin(fonctionDeNomination.getTypeUniteAdmin());
				model.addAttribute("unitesAdmins", unitesAdmins);
				nomination.setFonctionNomination(fonctionDeNomination);
			}
		}
		
		if(idUniteAdmin!=0)
		{
			if(uniteAdminDao.existsById(idUniteAdmin))
			{
				nomination.setUniteAdminDeNomination(uniteAdminDao.findById(idUniteAdmin).get());
			}
		}
		
		if(!dateNomination.equals(""))
		{
			SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_FROM_HTML_INPUT, Locale.US);
			try
			{
				nomination.setDateNomination(sdf.parse(dateNomination));
			}
			catch(Exception e){}
		}
		
		
		model.addAttribute("nomination", nomination);
		model.addAttribute("fonctionsNomination", fonctionDao.findByFonctionDeNominationTrue());
		
		return "nomination-promotion/nomination-promotion";
	}
	
	@GetMapping(path = "/staffadmin/unites-admins/{idUniteAdmin}/nominations/form")
	public String goToNominationForm2(Model model, HttpServletRequest request,
									 @PathVariable Long idUniteAdmin, 
									 @RequestParam(defaultValue = "") String matricule,
									 @RequestParam(defaultValue = "") String numActeNominaton,
									 @RequestParam(defaultValue = "0") long idFonction,
									 @RequestParam(defaultValue = "") String dateNomination) throws ParseException
	{
		
		UniteAdmin uniteAdmin = uniteAdminDao.findById(idUniteAdmin).get();
	
		List<Fonction> fonctionsNomination = fonctionDao.findByTypeUniteAdminIdTypeUniteAdmin(uniteAdmin.getTypeUniteAdmin().getIdTypeUniteAdmin());
		
		Nomination nomination = new Nomination();
		nomination.setUniteAdminDeNomination(uniteAdmin);
		nomination.setNumActeNominaton(numActeNominaton);
		if(!matricule.equals(""))
		{
			if(agentDao.existsByMatricule(matricule))
			{
				Agent agentANommer = agentDao.findByMatricule(matricule);
				nomination.setAgentNomme(agentANommer);
			}
		}
		
		if(!dateNomination.toString().equals(""))
		{
			SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
			try
			{
				nomination.setDateNomination(sdf.parse(dateNomination));
			}
			catch(Exception e) {}
		}
		
		if(idFonction!=0)
		{
			if(fonctionDao.existsById(idFonction))
			{
				Fonction fonction = fonctionDao.findById(idFonction).get();
				nomination.setFonctionNomination(fonction);
			}
		}
		model.addAttribute("nomination", nomination);
		model.addAttribute("fonctionsNomination", fonctionsNomination);
		
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
	public String goToConfirmationNomination(Model model, HttpServletRequest request, 
											 @ModelAttribute Nomination nomination,
											 @RequestParam(name="mode") String mode)
	{
		
		
		//System.out.println("FONCTION1111111 = "+fonctionNomination.getNomFonction());

		try
		{
			nominationValidation.validate(nomination);
		}
		catch (Exception e) 
		{
			if(e instanceof NominationException)
			{
				UniteAdmin uniteAdmin = uniteAdminDao.findById(nomination.getUniteAdminDeNomination().getIdUniteAdmin()).get();
				List<Fonction> fonctionsNomination;
				if(mode.equals(ModeEnum.FROM_UNITE_ADMIN.toString()))
				{
					fonctionsNomination = fonctionDao.findByTypeUniteAdminIdTypeUniteAdmin(uniteAdmin.getTypeUniteAdmin().getIdTypeUniteAdmin());
				}
				else
				{
					fonctionsNomination = fonctionDao.findByFonctionDeNominationTrue();
				}
			
				nomination.setUniteAdminDeNomination(uniteAdmin);
				
				model.addAttribute("nomination", nomination);
				model.addAttribute("fonctionsNomination", fonctionsNomination);
				model.addAttribute("globalErrorMsg", e.getMessage());
				return mode.equals(ModeEnum.FROM_UNITE_ADMIN.toString()) ? 
					   "nomination-promotion/nomination-unite-admin" : 
					   "nomination-promotion/nomination-promotion";
			}
		}
		String matricule = nomination.getAgentNomme().getMatricule();
		Long idAgent = nomination.getAgentNomme().getIdAgent();
		
		UniteAdmin uniteAdminDeNomination = uniteAdminDao.findById(nomination.getUniteAdminDeNomination().getIdUniteAdmin()).get();
		Fonction fonctionNomination = fonctionDao.findById(nomination.getFonctionNomination().getIdFonction()).get();
		
		fonctionNomination.setTypeUniteAdmin(uniteAdminDeNomination.getTypeUniteAdmin());
		Agent agentANommer = matricule!=null ? agentDao.findByMatricule(matricule) : agentDao.findById(idAgent).get();
		
		nomination.setAgentNomme(agentANommer);
		nomination.setUniteAdminDeNomination(uniteAdminDeNomination);
		nomination.setFonctionNomination(fonctionNomination);
		
		model.addAttribute("nomination", nomination);
		model.addAttribute("mode", mode);
		
		return "nomination-promotion/confirmation-nomination-unite-admin";
	}
}
