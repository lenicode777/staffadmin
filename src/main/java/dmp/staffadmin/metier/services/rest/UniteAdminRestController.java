package dmp.staffadmin.metier.services.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import dmp.staffadmin.dao.ITypeUniteAdminDao;
import dmp.staffadmin.dao.IUniteAdminDao;
import dmp.staffadmin.metier.entities.TypeUniteAdmin;
import dmp.staffadmin.metier.entities.UniteAdmin;
import dmp.staffadmin.metier.interfaces.IUniteAdminMetier;

@RestController
public class UniteAdminRestController 
{
	@Autowired private IUniteAdminDao uniteAdminDao;
	@Autowired private IUniteAdminMetier uniteAdminMetier;
	@Autowired private ITypeUniteAdminDao typeUniteAdminDao;
	@GetMapping(path = "/staffadmin/unites-admins")
	public UniteAdmin getUnitesAdmins(Model model)
	{
		UniteAdmin DGBF = uniteAdminDao.findBySigle("DGBF").get(0);
		return uniteAdminMetier.setSubAdminTree(DGBF);
	}

	@GetMapping(path = "/staffadmin/saf/frm-uniteAdmin/ajax/onTypeUniteAdminChange/{idTypeUniteAdmin}")
	public List<UniteAdmin> onTypeUniteAdminChange_ajax(@PathVariable Long idTypeUniteAdmin)
	{
		TypeUniteAdmin typeUniteAdmin = typeUniteAdminDao.getOne(idTypeUniteAdmin);
		List<UniteAdmin> unitesAdmins = uniteAdminDao.findByLevelLessThan(typeUniteAdmin.getAdministrativeLevel());
		return unitesAdmins;
	}
	
	private UniteAdmin setSubAdmin(UniteAdmin uniteAdmin)
	{
		List<UniteAdmin> subAdmins = uniteAdminDao.findByTutelleDirecte(uniteAdmin);
		uniteAdmin.setUniteAdminSousTutelle(subAdmins);
		subAdmins.forEach(subAdmin->
		{
			setSubAdmin(subAdmin);
		});
		return uniteAdmin;
	}
	
	
	public ArrayList getUnitesAdminsTree(UniteAdmin uniteAdmin)
	{
		ArrayList uaTree = new ArrayList<>();
		uniteAdmin = setSubAdmin( uniteAdmin);
		if(!uniteAdminDao.findByTutelleDirecte(uniteAdmin).isEmpty() && uniteAdminDao.findByTutelleDirecte(uniteAdmin)!=null)
		{
			for(UniteAdmin subUA:uniteAdminDao.findByTutelleDirecte(uniteAdmin))
			{
				Map map = new HashMap<>();
				map.put("text", subUA.toString());
				uaTree.add(map);
				map.put("nodes", getUnitesAdminsTree(subUA));
			}
		}
		return uaTree;
	}
	
	@GetMapping(path = "/staffadmin/unites-admins/toString")
	public ArrayList getUnitesAdminsTrees()
	{
		UniteAdmin DGBF = uniteAdminDao.findBySigle("DGBF").get(0);
		setSubAdmin(DGBF);
		return getUnitesAdminsTree(DGBF);
	}
	
	private Map getUnitesAdminsTrees_Generic(UniteAdmin uniteAdmin)
	{
		HashMap result = new HashMap<>();
		result.put("text", uniteAdmin.toString());
		//result.put("nodes", uniteAdmin.getUniteAdminSousTutelle().forEach(ua->{return getUnitesAdminsTrees_Generic(ua)}););
		if(uniteAdmin.getUniteAdminSousTutelle()!= null && !uniteAdmin.getUniteAdminSousTutelle().isEmpty())
		{
			uniteAdmin.getUniteAdminSousTutelle().forEach(uniteAdminSousTutelle->
			{
				result.put("nodes", getUnitesAdminsTrees_Generic(uniteAdminSousTutelle));
			});
		}
		else
		{
			
		}
		return result;
	}
}