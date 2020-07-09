package dmp.staffadmin.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController 
{

	
	@GetMapping(path ="/")
	public String getTemplate()
	{
		return "fragments/layout/layout";
		//return "test";
	}
	

}
