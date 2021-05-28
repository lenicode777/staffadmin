package dmp.staffadmin.controllers;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dmp.staffadmin.dao.IAgentDao;
import dmp.staffadmin.dao.IEmploiDao;
import dmp.staffadmin.dao.IFonctionDao;
import dmp.staffadmin.dao.IGradeDao;
import dmp.staffadmin.metier.constants.DateFormatConstants;
import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.metier.entities.Emploi;
import dmp.staffadmin.metier.entities.Fonction;
import dmp.staffadmin.metier.entities.Grade;
import dmp.staffadmin.metier.entities.Promotion;
import dmp.staffadmin.metier.enumeration.ErrorMsgEnum;
import dmp.staffadmin.metier.exceptions.PromotionException;
import dmp.staffadmin.metier.services.interfaces.IPromotionMetier;
import dmp.staffadmin.metier.validation.IValidation;
import dmp.staffadmin.security.aspects.AuthoritiesDtoAnnotation;

@Controller
public class PromotionController
{
	@Autowired
	private IFonctionDao fonctionDao;
	@Autowired
	private IEmploiDao emploiDao;
	@Autowired
	private IAgentDao agentDao;
	@Autowired
	private IGradeDao gradeDao;
	@Autowired
	private IPromotionMetier promotionMetier;
	@Autowired
	private IValidation<Promotion> promotionValidation;

	//@PreAuthorize("hasRole('SAF')")
	@GetMapping(path = "/staffadmin/promotion/form")
	@AuthoritiesDtoAnnotation
	public String goToFrmPromotion(Model model, HttpServletRequest request,
			@RequestParam(name = "idAgent", defaultValue = "0") Long idAgent,
			@RequestParam(defaultValue = "0") Long idFonction, @RequestParam(defaultValue = "0") Long idEmploi,
			@RequestParam(defaultValue = "0") Long idGrade, @RequestParam(defaultValue = "") String numActePromotion,
			@RequestParam(defaultValue = "") String datePromotion)
	{
		Promotion promotion = new Promotion();
		promotion.setNumActePromotion(numActePromotion);
		List<Fonction> fonctionsPromotion = fonctionDao.findByFonctionDeNominationFalse();
		List<Emploi> emploisPromotion = emploiDao.findAll();
		List<Grade> grades = gradeDao.findAll();

		Agent agentPromu = null;
		try
		{
			if (idAgent.longValue() == 0)
			{
				agentPromu = new Agent();
			} else
			{
				agentPromu = agentDao.findById(idAgent).get();
				grades = gradeDao.findByRangGreaterThan(agentPromu.getGrade().getRang());
			}
		} catch (Exception e)
		{
			if (e instanceof NoSuchElementException)
			{
				model.addAttribute("globalErrorMsg", "Références incorrectes");
			}
		}

		if (idFonction.longValue() != 0)
		{
			if (fonctionDao.existsById(idFonction))
			{
				Fonction newFonction = fonctionDao.findById(idFonction).get();
				fonctionsPromotion = fonctionDao.findByFonctionDeNominationFalse();
				model.addAttribute("unitesAdmins", fonctionsPromotion);
				promotion.setNewFonction(newFonction);
			}
		}

		if (idEmploi.longValue() != 0)
		{
			if (emploiDao.existsById(idEmploi))
			{
				Emploi newEmploi = emploiDao.findById(idEmploi).get();
				promotion.setNewEmploi(newEmploi);
			}
		}

		if (idGrade.longValue() != 0)
		{
			if (gradeDao.existsById(idGrade))
			{
				Grade newGrade = gradeDao.findById(idGrade).get();
				promotion.setNewGrade(newGrade);
			}
		}

		if (!datePromotion.equals(""))
		{
			SimpleDateFormat sdf = new SimpleDateFormat(DateFormatConstants.DATE_FORMAT_FROM_HTML_INPUT, Locale.US);
			try
			{
				promotion.setDatePromotion((sdf.parse(datePromotion)));
			} catch (Exception e)
			{
			}
		}

		promotion.setAgentPromu(agentPromu);
		model.addAttribute("cible", "promotion");
		model.addAttribute("grades", grades);
		model.addAttribute("promotion", promotion);
		model.addAttribute("fonctionsPromotion", fonctionsPromotion);
		model.addAttribute("emploisPromotion", emploisPromotion);
		return "nomination-promotion/nomination-promotion-tabs";
	}

	@PostMapping(path = "/staffadmin/promotion/confirmation")
	//@PreAuthorize("hasRole('SAF')")
	@AuthoritiesDtoAnnotation
	public String goToFrmConfirmationPromotion(Model model, HttpServletRequest request,
			@ModelAttribute Promotion promotion)
	{
		if (promotion.getAgentPromu().getIdAgent() == null)
		{
			promotion.setAgentPromu(agentDao.findByMatricule(promotion.getAgentPromu().getMatricule()));
		}
		try
		{
			promotionValidation.validate(promotion);
		} catch (PromotionException e)
		{
			model.addAttribute(ErrorMsgEnum.GLOBAL_ERROR_MSG.toString(), e.getMessage());
			return goToFrmPromotion(model, request, promotion.getAgentPromu().getIdAgent(),
					promotion.getNewFonction().getIdFonction(), promotion.getNewEmploi().getIdEmploi(),
					promotion.getNewGrade().getIdGrade(), promotion.getNumActePromotion(),
					promotion.getDatePromotion().toString());
		}

		promotion.setAgentPromu(agentDao.findById(promotion.getAgentPromu().getIdAgent()).get());
		promotion.setNewFonction(fonctionDao.findById(promotion.getNewFonction().getIdFonction()).get());
		promotion.setNewEmploi(emploiDao.findById(promotion.getNewEmploi().getIdEmploi()).get());
		promotion.setNewGrade(gradeDao.findById(promotion.getNewGrade().getIdGrade()).get());
		model.addAttribute("promotion", promotion);
		return "nomination-promotion/frm-confirmation-promotion";
	}

	@PostMapping(path = "/staffadmin/promotion/save")
	@PreAuthorize("hasRole('SAF')")
	public String savePromotion(Model model, HttpServletRequest request, @ModelAttribute Promotion promotion)
	{

		try
		{
			promotionMetier.save(promotion);
		} catch (PromotionException e)
		{
			model.addAttribute(ErrorMsgEnum.GLOBAL_ERROR_MSG.toString(), e.getMessage());
			return goToFrmPromotion(model, request, promotion.getAgentPromu().getIdAgent(),
					promotion.getNewFonction().getIdFonction(), promotion.getNewEmploi().getIdEmploi(),
					promotion.getNewGrade().getIdGrade(), promotion.getNumActePromotion(),
					promotion.getDatePromotion().toString());
		}

		return "redirect:/staffadmin/profil?idAgent=" + promotion.getAgentPromu().getIdAgent();
	}
}
