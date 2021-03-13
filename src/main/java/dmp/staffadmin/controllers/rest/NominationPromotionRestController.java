package dmp.staffadmin.controllers.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import dmp.staffadmin.dao.IFonctionDao;
import dmp.staffadmin.dao.ITypeUniteAdminDao;
import dmp.staffadmin.dao.IUniteAdminDao;
import dmp.staffadmin.metier.entities.Fonction;
import dmp.staffadmin.metier.entities.TypeUniteAdmin;
import dmp.staffadmin.metier.entities.UniteAdmin;

@RestController
public class NominationPromotionRestController 
{
	@Autowired private IUniteAdminDao uniteAdminDao;
	@Autowired private ITypeUniteAdminDao typeUniteAdminDao;
	@Autowired private IFonctionDao fonctionDao;
	@GetMapping(path = "/staffadmin/rest/frm-nomination/onFonctionDeNominationChange/{idFonction}")
	public List<UniteAdmin> onFonctionDeNominationChange_ajax(@PathVariable Long idFonction)
	{
		System.out.println("=============ID FONCTION===============");
		System.out.println(idFonction);
		System.out.println("============================");
		Fonction fonction = fonctionDao.findById(idFonction).get();
		TypeUniteAdmin typeUA = fonction.getTypeUniteAdmin();
		List<UniteAdmin> uniteAdmins = uniteAdminDao.findByTypeUniteAdmin(typeUA);
		return uniteAdmins;
	}
}
