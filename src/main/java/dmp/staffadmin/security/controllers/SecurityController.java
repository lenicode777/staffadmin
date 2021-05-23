package dmp.staffadmin.security.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dmp.staffadmin.dao.IAgentDao;
import dmp.staffadmin.dao.IArchiveAgentDao;
import dmp.staffadmin.metier.entities.UniteAdmin;
import dmp.staffadmin.metier.enumeration.TypeUniteAdminEnum;
import dmp.staffadmin.security.aspects.AuthoritiesDtoAnnotation;
import dmp.staffadmin.security.dao.AppRoleDao;
import dmp.staffadmin.security.dao.AppUserDao;
import dmp.staffadmin.security.model.AppRole;
import dmp.staffadmin.security.model.AppUser;
import dmp.staffadmin.security.services.IUserMetier;
import dmp.staffadmin.security.dto.ChangePasswordDto;

@Controller
//@Deprecated
public class SecurityController
{
	@Autowired
	private IUserMetier userMetier;
	@Autowired
	private AppUserDao userDao;
	@Autowired
	private AppRoleDao roleDao;
	@Autowired
	private IAgentDao agentDao;
	private @Autowired IArchiveAgentDao archiveAgentDao;

	public String addRoleToUser(AppUser user, AppRole role)
	{
		userMetier.addRoleToUser(user, role);
		// user.addRole(role);
		return null;
	}

	@GetMapping(path = "/staffadmin/logout")
	public String logout(HttpServletRequest request) throws ServletException
	{
		request.logout();
		return "redirect:/login";
	}

	@GetMapping(path = "/staffadmin/profil")
	@AuthoritiesDtoAnnotation
	public String goToProfile(HttpServletRequest request, Model model, @RequestParam(defaultValue = "0") Long idAgent)
	{
		String username = request.getUserPrincipal().getName();
		AppUser authUser = userDao.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("Nom d'utilisateur introuvable"));
		System.out.println("AuthUser = " + authUser.getUsername() + authUser.getIdUser());
		AppUser visitedUser = null;
		if (idAgent == null || idAgent == 0)
		{
			visitedUser = authUser;
		} else
		{
			visitedUser = userDao.findByAgentIdAgent(idAgent).orElseThrow(()->new UsernameNotFoundException("Aucun utilisateur trouvé pour un agent"));
			System.out.println("Visited user = " + visitedUser.getUsername() + visitedUser.getIdUser());
		}

		List<UniteAdmin> tutellesHierarchieTree = visitedUser
				.getAgent().getTutelleDirecte().getPatrentsStream().filter(ua -> !ua.getTypeUniteAdmin()
						.getNomTypeUniteAdmin().equals(TypeUniteAdminEnum.DIRECTION_GENERALE.toString()))
				.collect(Collectors.toList());

		model.addAttribute("listArchivesAgent",
				archiveAgentDao.findByAgentIdAgent(visitedUser.getAgent().getIdAgent()));
		model.addAttribute("tutellesHierarchieTree", tutellesHierarchieTree);
		model.addAttribute("modelRoles", roleDao.findAll());
		model.addAttribute("visitedUser", visitedUser);
		model.addAttribute("authUser", authUser);
		// model.addAttribute("userForm", new UserForm(user, "", "", ""));

		return "user/profil";
	}

	@GetMapping(path = "/staffadmin/change-password")
	@AuthoritiesDtoAnnotation
	public String gotoChangePassword(HttpServletRequest request, Model model)
	{
		String username = request.getUserPrincipal().getName();
		AppUser authUser = userDao.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("Nom d'utilisateur introuvable"));
		model.addAttribute("user", authUser);
		ChangePasswordDto userForm = new ChangePasswordDto(authUser, "", "", "");
		model.addAttribute("userForm", userForm);
		model.addAttribute("formError", null);
		model.addAttribute("displaySideMenu", authUser.getAgent().isActive());

		if (userForm.getUser().getAgent() == null)
		{
			System.out.println("=========================NULL POINTER EXCEPTION======================");
		}
		System.out.println("Matricule = " + userForm.getUser().getAgent().getMatricule());

		model.addAttribute("userForm", userForm);
		return "user/change-password";
	}

	@PostMapping(path = "/staffadmin/change-password")
	public String changePassword(Model model, @ModelAttribute ChangePasswordDto userForm)
	{
		System.out.println("HELLO");
		try
		{
			userMetier.changePassword(userForm);
		} catch (Exception e)
		{
			model.addAttribute("formError", e.getMessage());
			System.out.println(e.getMessage());
			return "user/change-password";
		}

		return "redirect:/staffadmin/logout";
	}

	@ExceptionHandler(Exception.class)
	public String exceptionHandler(Exception e, Model model)
	{
		System.out.println(e.getMessage());
		model.addAttribute("exceptionHandler", e.getMessage());
		return "exceptions/500";
	}
}
