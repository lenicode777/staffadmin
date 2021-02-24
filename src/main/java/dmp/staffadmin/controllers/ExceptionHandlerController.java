package dmp.staffadmin.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;


@Controller
public class ExceptionHandlerController 
{
	@ExceptionHandler(Exception.class)
	public String exceptionHandler(Exception e, Model model)
	{
		model.addAttribute("exceptionHandler", e.getMessage());
		return "exceptions/500";
	}
	/*
	nom 
	prenom
	numpiece
	datePiece
	lieuPiece
	Profession
	Employeur
	email
	bp
	tel
	service 
	matricule
	*/
}
