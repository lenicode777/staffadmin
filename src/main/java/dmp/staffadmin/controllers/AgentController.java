package dmp.staffadmin.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import dmp.staffadmin.dao.IAgentDao;
import dmp.staffadmin.dao.IDepartementDao;
import dmp.staffadmin.dao.IEmploiDao;
import dmp.staffadmin.dao.IFonctionDao;
import dmp.staffadmin.dao.IGradeDao;
import dmp.staffadmin.dao.ITypeUniteAdminDao;
import dmp.staffadmin.dao.IUniteAdminDao;
import dmp.staffadmin.metier.constants.ArchivageConstants;
import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.metier.entities.Emploi;
import dmp.staffadmin.metier.entities.Fonction;
import dmp.staffadmin.metier.entities.Grade;
import dmp.staffadmin.metier.entities.TypeUniteAdmin;
import dmp.staffadmin.metier.entities.UniteAdmin;
import dmp.staffadmin.metier.enumeration.ErrorMsgEnum;
import dmp.staffadmin.metier.enumeration.RoleEnum;
import dmp.staffadmin.metier.enumeration.TypeUniteAdminEnum;
import dmp.staffadmin.metier.exceptions.ArchivageException;
import dmp.staffadmin.metier.services.interfaces.IAgentMetier;
import dmp.staffadmin.metier.services.interfaces.IArchivageMetier;
import dmp.staffadmin.metier.services.interfaces.IEmploiMetier;
import dmp.staffadmin.metier.services.interfaces.IFonctionMetier;
import dmp.staffadmin.metier.services.interfaces.IGradeMetier;
import dmp.staffadmin.metier.services.interfaces.IUniteAdminMetier;
import dmp.staffadmin.security.aspects.AuthoritiesDtoAnnotation;
import dmp.staffadmin.security.dao.AppUserDao;
import dmp.staffadmin.security.model.AppUser;
import dmp.staffadmin.security.utilities.PrivilegeConstants;

@Controller
public class AgentController
{
	@Autowired
	private IEmploiDao emploiDao;
	@Autowired
	private IFonctionDao fonctionDao;
	@Autowired
	private IGradeDao gradeDao;

	@Autowired
	private IEmploiMetier emploiMetier;
	@Autowired
	private IFonctionMetier fonctionMetier;
	@Autowired
	private IGradeMetier gradeMetier;

	@Autowired
	private IAgentDao agentDao;
	@Autowired
	private IAgentMetier agentMetier;
	@Autowired
	private IUniteAdminMetier uniteAdminMetier;

	@Autowired
	private IArchivageMetier archivageMetier;

	@Autowired
	private AppUserDao userDao;
	@Autowired
	private IUniteAdminDao uniteAdminDao;
	@Autowired
	private ITypeUniteAdminDao typeUniteAdminDao;
	@Autowired
	private IDepartementDao departementDao;

	@PreAuthorize("hasAuthority('"+ PrivilegeConstants.CONSULTER_LISTE_PERSONNEL + "')")
	@GetMapping(path = "/staffadmin/list-agents")
	@AuthoritiesDtoAnnotation
	public String goToListAgent(Model model, HttpServletRequest request)
	{
		List<Agent> listAgents = new ArrayList<>();
		AppUser authUser = userDao.findByUsername(request.getUserPrincipal().getName()).orElseThrow(()->new UsernameNotFoundException("Username introuvable"));;

	
		if (authUser.getIdUaChampVisuel() == null)
		{
			listAgents = new ArrayList<>();
		} 
		else
		{
			UniteAdmin visibilite = uniteAdminDao.findById(authUser.getIdUaChampVisuel()).get();
			listAgents = uniteAdminMetier.getAllPersonnel(visibilite);
			model.addAttribute("visibilite", visibilite);
		}
		TypeUniteAdmin typeUA = typeUniteAdminDao
				.findByNomTypeUniteAdmin(TypeUniteAdminEnum.DIRECTION_CENTRALE.toString());
		List<UniteAdmin> listDCs = uniteAdminDao.findByTypeUniteAdmin(typeUA);
		model.addAttribute("listDCs", listDCs);
		model.addAttribute("listAgents", listAgents);
		model.addAttribute("formCritere", new AgentFormCritere());

		model.addAttribute("attrIdDc", "");
		model.addAttribute("attrIdSd", "");
		model.addAttribute("attrIdService", "");
		model.addAttribute("uniteAdminHierarchie", "DGMP > ");

		return "agent/list/list-agent";
	}

	@GetMapping(path = "/staffadmin/agents/search")
	@AuthoritiesDtoAnnotation
	public String search(Model model, HttpServletRequest request, @RequestParam(defaultValue = "0") long idDc,
			@RequestParam(defaultValue = "0") long idSd, @RequestParam(defaultValue = "0") long idService)
	{
		AppUser authUser = userDao.findByUsername(request.getUserPrincipal().getName()).orElseThrow(()->new UsernameNotFoundException("Username introuvable")); // Recupération de l'utilisateur
																						// authentifié
		List<Agent> listAgentsVisibles = new ArrayList<>();
		UniteAdmin dc = null;
		UniteAdmin sd = null;
		UniteAdmin service = null;

		// Transmission des identifiants des Unités admin choisis ainsi que des listes
		// de SD et Services à afficher dans les listes déroulantes
		if (idDc == 0)
		{
			model.addAttribute("attrIdDc", "");
		} else
		{
			model.addAttribute("attrIdDc", uniteAdminDao.findById(idDc).get().getIdUniteAdmin());
			model.addAttribute("listSds", uniteAdminDao.findByTutelleDirecteIdUniteAdmin(idDc));
		}

		if (idSd == 0)
		{
			model.addAttribute("attrIdSd", "");
		} else
		{
			model.addAttribute("attrIdSd", uniteAdminDao.findById(idSd).get().getIdUniteAdmin());

			List<UniteAdmin> listSds = uniteAdminDao.findByTutelleDirecteIdUniteAdmin(idDc);
			model.addAttribute("listSds", listSds);
			model.addAttribute("listServices", uniteAdminDao.findByTutelleDirecteIdUniteAdmin(idSd));
		}

		if (idService == 0)
		{
			model.addAttribute("attrIdService", "");
		} else
		{
			model.addAttribute("attrIdService", uniteAdminDao.findById(idService).get().getIdUniteAdmin());
			service = uniteAdminDao.findById(idService).get();

			List<UniteAdmin> listServices = uniteAdminDao.findByTutelleDirecteIdUniteAdmin(idSd);
			model.addAttribute("listServices", listServices);
		}

		List<String> roles = authUser.getRoles().stream().map(r -> r.getRoleName()).collect(Collectors.toList());
		if (!roles.contains("RESPONSABLE"))
		{
			throw new RuntimeException("Accès réfusé");
		} else if (roles.contains("SAF"))
		{
			listAgentsVisibles = agentDao.findAll();
		} else
		{
			// listAgentsVisibles =
			// agentDao.findByTutelleDirecte(authUser.getAgent().getTutelleDirecte());
			listAgentsVisibles = authUser.getAgent().getTutelleDirecte().getSubAdminStream()
					.map(ua -> ua.getPersonnel()).flatMap(listAgents -> listAgents.stream())
					.collect(Collectors.toList());
		}

		List<Long> listIdUaRecherchees = Arrays.asList(idDc, idSd, idService);
		UniteAdmin uaRecherchee;
		try
		{
			uaRecherchee = listIdUaRecherchees.stream().filter(id -> id != 0)
					.map(id -> uniteAdminDao.findById(id).get()).max(new Comparator<UniteAdmin>()
					{
						@Override
						public int compare(UniteAdmin ua1, UniteAdmin ua2)
						{
							return Integer.valueOf(ua1.getLevel()).compareTo(Integer.valueOf(ua2.getLevel()));
						}
					}).get();
		} catch (NoSuchElementException e)
		{
			uaRecherchee = uniteAdminDao.findById(1L).get();
			// e.printStackTrace();
		}
		System.out.println("UA RECHERCHEE = " + uaRecherchee.toString() + "");

		// List<Long> listIdAgentVisible =
		// listAgentsVisibles.stream().map(a->a.getIdAgent()).collect(Collectors.toList());

		// List<Agent> listAgents =
		// agentDao.findByTutelleDirecte(uaRecherchee).stream().filter(a->listIdAgentVisible.contains(a.getIdAgent())).collect(Collectors.toList());

		List<Agent> listAgents = uniteAdminMetier.getAllPersonnel(uaRecherchee);
		TypeUniteAdmin typeUA = typeUniteAdminDao.findByNomTypeUniteAdmin("DIRECTION CENTRALE");

		List<UniteAdmin> listDCs = uniteAdminDao.findByTypeUniteAdmin(typeUA);
		model.addAttribute("listDCs", listDCs);
		model.addAttribute("listAgents", listAgents);
		model.addAttribute("formCritere",
				new AgentFormCritere(idDc, idSd, idService, null, null, null, null, null, null));

		List<String> uniteAdminHierarchie = uaRecherchee.getPatrentsStream().map(ua -> ua.getSigle() + " > ")
				.collect(Collectors.toList());
		List<String> hierarchie = new ArrayList<>();
		for (int i = uniteAdminHierarchie.size() - 1; i >= 0; i--)
		{
			hierarchie.add(uniteAdminHierarchie.get(i));
		}
		model.addAttribute("uniteAdminHierarchie", uniteAdminHierarchie);

		return "agent/list/list-agent";
	}

	// @PreAuthorize("hasAuthority('SAF')")
	@GetMapping(path = "/staffadmin/frm-agent")
	@AuthoritiesDtoAnnotation
	public String goToFormAgent(Model model, HttpServletRequest request, @RequestParam(defaultValue = "0") Long idAgent)
	{
		Agent agent = new Agent();
		Map modelAttributes = new HashMap<>();
		modelAttributes.put("agent", agent);
		modelAttributes.put("emplois", emploiDao.findAll());
		modelAttributes.put("fonctions", fonctionDao.findByFonctionDeNominationFalse());
		modelAttributes.put("grades", gradeDao.findAll());
		modelAttributes.put("departements", departementDao.findAll());

		if (idAgent == 0)
		{
			model.addAttribute("mode", "new");
			model.addAllAttributes(modelAttributes);
			return "agent/frm/frm-agent";
		} else
		{
			model.addAttribute("mode", "update");
			agent = agentDao.findById(idAgent).get();
			modelAttributes.put("agent", agent);
			model.addAllAttributes(modelAttributes);
			return "agent/frm/frm-update-agent";
		}
	}

	@PostMapping(path = "/staffadmin/agents/save")
	@AuthoritiesDtoAnnotation
	public String saveNewAgent(Model model, HttpServletRequest request, @ModelAttribute Agent agent, BindingResult bindingResult,
			@RequestParam String mode)
	{
		try
		{
			if (mode.equals("new"))
			{
				agentMetier.recruter(agent);
			} else if (mode.equals("update"))
			{
				agentMetier.update(agent.getIdAgent(), agent);
			}
		} catch (RuntimeException e)
		{
			System.out.println("========================================");
			System.out.println(e.getMessage());
			e.printStackTrace();
			System.out.println("========================================");

			List<Emploi> emplois = emploiDao.findAll();
			List<Fonction> fonctions = fonctionDao.findAll();
			List<Grade> grades = gradeDao.findAll();

			Map modelAttributes = new HashMap<>();
			agent.setEmploi(new Emploi());
			agent.setFonction(new Fonction());
			agent.setGrade(new Grade());

			modelAttributes.put("agent", agent);
			modelAttributes.put("emplois", emploiDao.findAll());
			modelAttributes.put("fonctions", fonctionDao.findAll());
			modelAttributes.put("grades", gradeDao.findAll());

			model.addAllAttributes(modelAttributes);
			model.addAttribute("errorMsg", e.getMessage());

			if (mode.equals("new"))
			{
				return "agent/frm/frm-agent";
			} else if (mode.equals("update"))
			{
				return "agent/frm/frm-update-agent";
			}

			return "agent/frm/frm-agent";
		}
		return "redirect:/staffadmin/profil?idAgent=" + agent.getIdAgent();
	}

	@GetMapping(path = "/staffadmin/photo-agent/{idAgent}", produces = { MediaType.IMAGE_PNG_VALUE,
			MediaType.IMAGE_JPEG_VALUE })
	public @ResponseBody byte[] getPhoto(@PathVariable("idAgent") Long idAgent) throws IOException
	{
		Agent agent = agentDao.findById(idAgent).get();
		return Files.readAllBytes(
				Paths.get(new File(ArchivageConstants.AGENT_UPLOADS_DIR + "/photo/" + agent.getNomPhoto()).toURI()));
	}

	@PostMapping(path = "/staffadmin/uploadPhoto/{idAgent}")
	public String uploadPhoto(Model model, HttpServletRequest request, @RequestParam(name = "photo") MultipartFile file,
			@PathVariable Long idAgent) throws IOException
	{
		long maxSize = ArchivageConstants.UPLOAD_MAX_SIZE;
		List<String> authorizedTypes = ArchivageConstants.PHOTO_AUTHORIZED_TYPE;

		if (file.isEmpty())
		{
			return "redirect:/staffadmin/profil?idAgent=" + idAgent;
		}
		long fileSize = file.getSize();
		String fileExtension = archivageMetier.getFileExtension(file);
		try
		{
			if (fileSize > maxSize)
				throw new ArchivageException("Taille de fichier > " + (maxSize / 1048576) + "Mo");

			if (!authorizedTypes.stream().anyMatch(type -> type.equalsIgnoreCase(fileExtension)))
				throw new ArchivageException("Type de fichier non pris en charge");
		} catch (Exception e)
		{
			model.addAttribute(ErrorMsgEnum.ERROR_MSG.toString(), e.getMessage());
		}
		Agent agent = agentDao.findById(idAgent).get();
		agent.setNomPhoto(idAgent + fileExtension);

		Files.write(Paths.get(ArchivageConstants.AGENT_UPLOADS_DIR + "/photo/" + agent.getNomPhoto()), file.getBytes());

		agentDao.save(agent);

		return "redirect:/staffadmin/profil?idAgent=" + idAgent;
	}

	// @ExceptionHandler(Exception.class)
	public String exceptionHandler(Exception e, Model model)
	{
		System.out.println(e.getMessage());
		model.addAttribute("exceptionHandler", e.getMessage());
		return "exceptions/500";
	}
}
