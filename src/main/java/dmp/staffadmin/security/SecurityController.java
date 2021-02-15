package dmp.staffadmin.security;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dmp.staffadmin.dao.IAgentDao;
import dmp.staffadmin.security.userdetailsservice.IRoleDao;
import dmp.staffadmin.security.userdetailsservice.IUserDao;
import dmp.staffadmin.security.userdetailsservice.IUserMetier;
import dmp.staffadmin.security.userdetailsservice.Role;
import dmp.staffadmin.security.userdetailsservice.User;
import dmp.staffadmin.security.userdetailsservice.UserForm;

@Controller
public class SecurityController 
{
	@Autowired private IUserMetier userMetier;
	@Autowired private IUserDao userDao;
	@Autowired private IRoleDao roleDao;
	@Autowired private IAgentDao agentDao;
	public String addRoleToUser(User user, Role role)
	{
		userMetier.addRoleToUser(user, role);
		//user.addRole(role);
		return null;
	}
	
	@GetMapping(path = "/staffadmin/logout")
	public String logout(HttpServletRequest request) throws ServletException
	{
		request.logout();
		return "redirect:/login";
	}
	
	@GetMapping(path = "/staffadmin/profil")
	public String goToProfile(HttpServletRequest request, Model model, @RequestParam(defaultValue = "0") Long idAgent)
	{
		String username = request.getUserPrincipal().getName();
		User authUser = userDao.findByUsername(username);
		User visitedUser = null ;
		if(idAgent==null || idAgent==0)
		{
			visitedUser=authUser;
		}
		else
		{
			visitedUser = agentDao.findById(idAgent).get().getUser();
		}
		
		model.addAttribute("modelRoles", roleDao.findAll());
		model.addAttribute("user", visitedUser);
		model.addAttribute("displaySideMenu", visitedUser.getAgent().isActive());
		//model.addAttribute("userForm", new UserForm(user, "", "", ""));
	
		return "user/profil";
	}
	
	@GetMapping(path = "/staffadmin/change-password")
	public String gotoChangePassword(HttpServletRequest request, Model model)
	{
		String username = request.getUserPrincipal().getName();
		User user = userDao.findByUsername(username);
		model.addAttribute("user", user);
		UserForm userForm = new UserForm(user, "", "", "");
		model.addAttribute("userForm", userForm);
		model.addAttribute("formError", null);
		model.addAttribute("displaySideMenu", user.getAgent().isActive());
		
		if(userForm.getUser().getAgent()==null)
		{
			System.out.println("=========================NULL POINTER EXCEPTION======================");
		}
		System.out.println("Matricule = " + userForm.getUser().getAgent().getMatricule());
		
		model.addAttribute("userForm", userForm);
		return "user/change-password";
	}
	
	@PostMapping(path = "/staffadmin/change-password")
	public String changePassword(Model model, @ModelAttribute UserForm userForm)
	{
		System.out.println("HELLO");
		try
		{
			userMetier.changePassword(userForm);
		}
		catch (Exception e) 
		{
			model.addAttribute("formError",e.getMessage());
			System.out.println(e.getMessage());
			return "user/change-password";
		}
		
		return "redirect:/staffadmin/logout";
	}
}
