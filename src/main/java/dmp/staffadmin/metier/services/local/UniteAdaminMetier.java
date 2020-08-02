package dmp.staffadmin.metier.services.local;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dmp.staffadmin.dao.IUniteAdminDao;
import dmp.staffadmin.metier.entities.UniteAdmin;
import dmp.staffadmin.metier.interfaces.IUniteAdminMetier;
import dmp.staffadmin.metier.validation.IUniteAdminValidation;
@Service
public class UniteAdaminMetier implements IUniteAdminMetier
{
	@Autowired private IUniteAdminDao uniteAdminDao;
	@Autowired private IUniteAdminValidation uniteAdminValidation;

	@Override
	public UniteAdmin save(UniteAdmin uniteAdmin) 
	{
		uniteAdminValidation.validate(uniteAdmin);
		UniteAdmin tutelleDirecte = uniteAdminDao.getOne(uniteAdmin.getTutelleDirecte().getIdUniteAdmin());
		tutelleDirecte.ajouterUA(uniteAdmin);
		return uniteAdminDao.save(uniteAdmin);
	}
	@Override
	public UniteAdmin update(UniteAdmin uniteAdmin) 
	{
		this.save(uniteAdmin);
		return null;
	}
	@Override
	public UniteAdmin update(Long uniteAdminId, UniteAdmin uniteAdminBody) 
	{
		uniteAdminBody.setIdUniteAdmin(uniteAdminId);
		this.save(uniteAdminBody);
		return null;
	}
	@Override
	public List<UniteAdmin> findAll() 
	{
		return null;
	}
	@Override
	public UniteAdmin setSubAdminTree(UniteAdmin uniteAdmin) 
	{
		List<UniteAdmin> subAdmins = uniteAdminDao.findByTutelleDirecte(uniteAdmin);
		uniteAdmin.setUniteAdminSousTutelle(subAdmins);
		subAdmins.forEach(subAdmin->
		{
			setSubAdminTree(subAdmin);
		});
		return uniteAdmin;
	}
}
