package dmp.staffadmin.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import dmp.staffadmin.dao.IAgentDao;
import dmp.staffadmin.security.aspects.AuthoritiesDtoAnnotation;

@Controller
public class AnnuaireController
{
	@Autowired
	private IAgentDao agentDao;

	@GetMapping(path = "/staffadmin/annuaire")
	//@PreAuthorize("hasRole('AGENT')")
	@AuthoritiesDtoAnnotation
	String goToAnnuaire(Model model, HttpServletRequest request)
	{

		model.addAttribute("listAgents", agentDao.findAll());
		return "annuaire/annuaire";
	}
}
