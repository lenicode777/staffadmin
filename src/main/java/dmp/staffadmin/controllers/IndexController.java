package dmp.staffadmin.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import dmp.staffadmin.security.aspects.AuthoritiesDtoAnnotation;
import dmp.staffadmin.security.dao.AppUserDao;

@Controller
public class IndexController
{
	@Autowired
	private AppUserDao userDao;

	@GetMapping(path = "/")
	@AuthoritiesDtoAnnotation
	public String getTemplate(Model model, HttpServletRequest request)
	{
		model.addAttribute("displaySideMenu",
				userDao.findByUsername(request.getUserPrincipal().getName())
				.orElseThrow(()->new UsernameNotFoundException("Username introuvable")).getAgent().isActive());
		return "fragments/layout/layout";
		// return "test";
	}
}
