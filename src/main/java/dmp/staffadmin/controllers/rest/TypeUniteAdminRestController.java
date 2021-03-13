package dmp.staffadmin.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import dmp.staffadmin.dao.IFonctionDao;
import dmp.staffadmin.dao.ITypeUniteAdminDao;
import dmp.staffadmin.metier.entities.TypeUniteAdmin;

public class TypeUniteAdminRestController
{
	@Autowired private ITypeUniteAdminDao typeUniteAdminDao;
	@Autowired private IFonctionDao fonctionDao;
	
	@GetMapping(path = "/staffadmin/rest/typeUniteAdmins/{idTypeUniteAdmin}")
	public TypeUniteAdmin findById(@PathVariable Long idTypeUniteAdmin)
	{
		return typeUniteAdminDao.findById(idTypeUniteAdmin).get();
	}
	
	@GetMapping(path = "/staffadmin/rest/typeUniteAdmins/findByIdFonction/{idFonction}")
	public TypeUniteAdmin findByIdFonction(@PathVariable Long idFonction)
	{
		return fonctionDao.findById(idFonction).get().getTypeUniteAdmin();
	}
}
