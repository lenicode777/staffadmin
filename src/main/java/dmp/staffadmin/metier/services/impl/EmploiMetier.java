package dmp.staffadmin.metier.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dmp.staffadmin.dao.IEmploiDao;
import dmp.staffadmin.metier.entities.Emploi;
import dmp.staffadmin.metier.services.interfaces.IEmploiMetier;

@Service
public class EmploiMetier implements IEmploiMetier
{
	@Autowired private IEmploiDao emploiDao;
	@Override
	public Emploi findByNom(String nomEmploi) 
	{
		return emploiDao.findByNomEmploi(nomEmploi).isEmpty() ? null : emploiDao.findByNomEmploi(nomEmploi).get(0);
	}

	@Override
	public Emploi save(Emploi emploi) 
	{
		emploi.setNomEmploi(emploi.getNomEmploi().toUpperCase());
		return emploiDao.save(emploi);
	}
	
	@Override
	public Emploi update(Emploi e) 
	{
		return save(e);
	}
	
	@Override
	public Emploi update(Long emploiId, Emploi emploiBody) 
	{
		emploiBody.setIdEmploi(emploiId);
		return save(emploiBody);
	}



	@Override
	public List<Emploi> findAll() 
	{
		return emploiDao.findAll();
	}

}
