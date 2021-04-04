package dmp.staffadmin.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import dmp.staffadmin.security.userdetailsservice.IUserDao;

@Controller
public class IndexController
{
	@Autowired
	private IUserDao userDao;

	@GetMapping(path = "/")
	public String getTemplate(Model model, HttpServletRequest request)
	{
		model.addAttribute("displaySideMenu",
				userDao.findByUsername(request.getUserPrincipal().getName()).getAgent().isActive());
		return "fragments/layout/layout";
		// return "test";
	}
}
