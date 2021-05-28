package dmp.staffadmin.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.shaded.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dmp.staffadmin.dao.IAgentDao;
import dmp.staffadmin.dao.IArchiveAgentDao;
import dmp.staffadmin.dao.ITypeArchiveDao;
import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.metier.entities.ArchiveAgent;
import dmp.staffadmin.metier.entities.TypeArchive;
import dmp.staffadmin.metier.enumeration.ErrorMsgEnum;
import dmp.staffadmin.metier.services.interfaces.IArchivageAgentMetier;
import dmp.staffadmin.metier.services.interfaces.IArchivageMetier;
import dmp.staffadmin.metier.validation.IValidation;
import dmp.staffadmin.security.aspects.AuthoritiesDtoAnnotation;

@Controller
public class ArchivageAgentController
{
	private @Autowired IArchivageMetier archivageMetier;
	private @Autowired IArchivageAgentMetier archivageAgentMetier;
	private @Autowired IArchiveAgentDao archiveAgentDao;
	private @Autowired IAgentDao agentDao;
	private @Autowired ITypeArchiveDao typeArchiveDao;
	private @Autowired IValidation<ArchiveAgent> archiveValidation;

	// @PreAuthorize("hasRole('DRH')")
	@GetMapping(path = "/staffadmin/archivage/agents")
	@AuthoritiesDtoAnnotation
	public String goToArchivageAgentForm(Model model, HttpServletRequest request,
			@RequestParam(defaultValue = "", name = "matricule") String matricule)
	{
		ArchiveAgent archiveAgent = new ArchiveAgent();

		Agent agent;
		if (matricule.equals(""))
		{
			agent = null;
		} else
		{
			if (agentDao.existsByMatricule(matricule))
			{
				agent = agentDao.findByMatricule(matricule);
				archiveAgent.setAgent(agent);
			} else
			{
				model.addAttribute(ErrorMsgEnum.ERROR_MSG.toString(), "Matricule inexistant!");
				agent = null;
			}
		}
		List<TypeArchive> listTypeArchive = typeArchiveDao.findAll();

		List<TypeArchive> typesArchive = typeArchiveDao.findAll();

		model.addAttribute("listTypeArchive", listTypeArchive);
		model.addAttribute("typesArchive", typesArchive);
		model.addAttribute("agent", agent);
		// model.addAttribute("typeFile", "");
		model.addAttribute("archiveAgent", archiveAgent);
		return "archivage/agent/archivage-agent";
	}

	@PostMapping(path = "/staffadmin/archivage/agent/upload")
	public String saveArchiveAgent(Model model,HttpServletRequest request ,@ModelAttribute ArchiveAgent archiveAgent)
	{
		Agent agent;
		try
		{
			archivageAgentMetier.uploadArchiveAgent(archiveAgent);
		} catch (RuntimeException e)
		{
			agent = agentDao.findById(archiveAgent.getAgent().getIdAgent()).get();
			model.addAttribute(ErrorMsgEnum.ERROR_MSG.toString(), e.getMessage());
			return goToArchivageAgentForm(model, request, agent.getMatricule());
		}

		return "redirect:/staffadmin/profil?idAgent=" + archiveAgent.getAgent().getIdAgent();
	}

	@GetMapping(path = "/staffadmin/archiveAgent/download/{idArchiveAgent}")
	@ResponseBody
	public void downloadArchiveAgent(Model model, @PathVariable("idArchiveAgent") Long idArchiveAgent,
			HttpServletResponse response)
	{
		ArchiveAgent archiveAgent = archiveAgentDao.findById(idArchiveAgent).get();

		response.setHeader("Content-Disposition",
				"attachement; filename=" + FilenameUtils.getName(archiveAgent.getPath()));
		response.setHeader("Content-Transfert-Encoding", "binary");
		try
		{
			archivageAgentMetier.downloadArchiveAgent(idArchiveAgent, response);
		} catch (RuntimeException e)
		{
			model.addAttribute(ErrorMsgEnum.ERROR_MSG.toString(), e.getMessage());
		}
	}

	@GetMapping(path = "/staffadmin/archiveAgent/delete/{idArchiveAgent}")
	@AuthoritiesDtoAnnotation
	public String deleteArchiveAgent(Model model, HttpServletRequest request, @PathVariable("idArchiveAgent") Long idArchiveAgent,
			@RequestParam Long idAgent)
	{
		archivageAgentMetier.deleteArchiveAgent(idArchiveAgent);
		return "redirect:/staffadmin/profil?idAgent=" + idAgent;
	}
}
