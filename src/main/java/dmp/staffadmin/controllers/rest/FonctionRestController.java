package dmp.staffadmin.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import dmp.staffadmin.dao.IFonctionDao;
import dmp.staffadmin.metier.entities.Fonction;

@RestController
public class FonctionRestController 
{
	@Autowired private IFonctionDao fonctionDao;
	@GetMapping(path = "/staffadmin/rest/fonctions/{idFonction}")
	public Fonction findById(@PathVariable Long idFonction)
	{
		return fonctionDao.findById(idFonction).get();
	}
}
