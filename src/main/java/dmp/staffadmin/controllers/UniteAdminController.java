package dmp.staffadmin.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UniteAdminController 
{
	@GetMapping(path = "/sataffadmin/unite-admin")
	public String goToUniteAdmin(Model model)
	{
		
		return "unite-admin/unite-admin";
	}
}
