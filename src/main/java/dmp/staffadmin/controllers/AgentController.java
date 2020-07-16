package dmp.staffadmin.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import dmp.staffadmin.dao.IAgentDao;
import dmp.staffadmin.dao.IEmploiDao;
import dmp.staffadmin.dao.IFonctionDao;
import dmp.staffadmin.dao.IGradeDao;
import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.metier.entities.Emploi;
import dmp.staffadmin.metier.entities.Fonction;
import dmp.staffadmin.metier.entities.Grade;
import dmp.staffadmin.metier.interfaces.IAgentMetier;
import dmp.staffadmin.metier.interfaces.IEmploiMetier;
import dmp.staffadmin.metier.interfaces.IFonctionMetier;
import dmp.staffadmin.metier.interfaces.IGradeMetier;
import dmp.staffadmin.metier.services.local.EmploiMetier;

@Controller
public class AgentController 
{
	@Autowired private IEmploiDao emploiDao;
	@Autowired private IFonctionDao fonctionDao;
	@Autowired private IGradeDao gradeDao;
	
	@Autowired private IEmploiMetier emploiMetier;
	@Autowired private IFonctionMetier fonctionMetier;
	@Autowired private IGradeMetier gradeMetier;
	
	@Autowired private IAgentDao agentDao;
	@Autowired private IAgentMetier agentMetier;
	
	@GetMapping(path ="/saf/frm-agent")
	public String goToFormAgent(Model model)
	{
		String nomEmploi="";
		String nomFonction="";
		String nomGrade="";
		
		List<Emploi> emplois = emploiDao.findAll();
		List<Fonction> fonctions = fonctionDao.findAll();
		List<Grade> grades = gradeDao.findAll();

		Map modelAttributes = new HashMap<>();
		Agent agent = new Agent();
		agent.setEmploi(new Emploi());
		agent.setFonction(new Fonction());
		agent.setGrade(new Grade());
		
		modelAttributes.put("agent", agent);
		modelAttributes.put("emplois", emploiDao.findAll());
		modelAttributes.put("fonctions", fonctionDao.findAll());
		modelAttributes.put("grades", gradeDao.findAll());
		modelAttributes.put("nomEmploi", nomEmploi);
		modelAttributes.put("nomFonction", nomFonction);
		modelAttributes.put("nomGrade", nomGrade);
		
		//model.addAttribute("agent", new Agent()); 
		model.addAllAttributes(modelAttributes); 
		return "agent/frm/frm-agent";
		//return "test";
	}
	
	@PostMapping(path = "/agents/save")
	public String saveNewAgent(Model model, Agent agent, String nomEmploi, String  nomFonction, String  nomGrade, BindingResult bindingResult)
	{
		//System.out.println("========================================");
		//System.out.println(agent.getEmploi().getNomEmploi());
		//System.out.println("========================================");
		
		//agent.setEmploi( emploiDao.findByNomEmploi(nomEmploi).get(0));
		//agent.setFonction(fonctionDao.findByNomFonction(nomFonction).get(0));
		//agent.setGrade(gradeDao.findByNomGrade(nomGrade).get(0));
		//System.out.println(agent);
		//Emploi emploi = emploiMetier.findByNom(nomEmploi);
		//Fonction fonction = fonctionMetier.findByNom(nomFonction);
		//Grade grade = gradeMetier.findByNom(nomGrade);
		agent.setEmploi(emploiMetier.findByNom(nomEmploi));
		agent.setFonction(fonctionMetier.findByNom(nomFonction));
		agent.setGrade(gradeMetier.findByNom(nomGrade));
		try 
		{
			agentMetier.recruter(agent);
		} 
		catch (RuntimeException e) 
		{
			System.out.println("========================================");
			System.out.println(e.getMessage());
			System.out.println("========================================");
			
			List<Emploi> emplois = emploiDao.findAll();
			List<Fonction> fonctions = fonctionDao.findAll();
			List<Grade> grades = gradeDao.findAll();
			
			nomEmploi = agent.getEmploi().getNomEmploi();
			nomFonction = agent.getFonction().getNomFonction();
			nomGrade = agent.getGrade().getNomGrade();

			Map modelAttributes = new HashMap<>();
			agent.setEmploi(new Emploi());
			agent.setFonction(new Fonction());
			agent.setGrade(new Grade());
			
			modelAttributes.put("agent", agent);
			modelAttributes.put("emplois", emploiDao.findAll());
			modelAttributes.put("fonctions", fonctionDao.findAll());
			modelAttributes.put("grades", gradeDao.findAll());
			modelAttributes.put("nomEmploi", nomEmploi);
			modelAttributes.put("nomFonction", nomFonction);
			modelAttributes.put("nomGrade", nomGrade);
			model.addAllAttributes(modelAttributes);
			model.addAttribute("errorMsg", e.getMessage());

			//e.printStackTrace();
			return "agent/frm/frm-agent";
		}
		return "redirect:/";
	}
}
