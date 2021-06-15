package dmp.staffadmin.metier.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dmp.staffadmin.dao.IFonctionDao;
import dmp.staffadmin.metier.entities.Fonction;
import dmp.staffadmin.metier.services.interfaces.IFonctionMetier;

@Service
public class FonctionMetier implements IFonctionMetier 
{
	@Autowired private IFonctionDao fonctionDao;

	@Override
	public Fonction save(Fonction fonction)
	{
		fonction.setNomFonction(fonction.getNomFonction().toUpperCase());
		return fonctionDao.save(fonction);
	}

	@Override
	public Fonction update(Fonction f)
	{
		return save(f);
	}

	@Override
	public Fonction update(Long fonctionId, Fonction fonctionBody) 
	{
		fonctionBody.setIdFonction(fonctionId);
		return save(fonctionBody);
	}

	@Override
	public List<Fonction> findAll()
	{
		return fonctionDao.findAll();
	}

	@Override
	public Fonction findByNom(String nomFonction)
	{
		return fonctionDao.findByNomFonction(nomFonction).isEmpty() ? null:fonctionDao.findByNomFonction(nomFonction).get(0);
	}
	
}
