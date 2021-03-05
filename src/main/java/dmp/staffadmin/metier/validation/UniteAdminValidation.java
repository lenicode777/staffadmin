package dmp.staffadmin.metier.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import dmp.staffadmin.dao.IUniteAdminDao;
import dmp.staffadmin.metier.entities.UniteAdmin;
import dmp.staffadmin.metier.exceptions.UniteAdminException;
@Service
public class UniteAdminValidation implements IUniteAdminValidation
{
	@Autowired private IUniteAdminDao uniteAdminDao;
	@Override
	public boolean isValide(UniteAdmin e) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(UniteAdmin uniteAdmin) 
	{
		if(uniteAdmin.getAppellation().equals("") || uniteAdmin.getAppellation()==null)
		{
			throw new RuntimeException("Le nom de l'unité d'administration est vide");
		}
		if(uniteAdmin.getSigle().equals("") || uniteAdmin.getSigle()==null)
		{
			throw new RuntimeException("Le sigle de l'unité d'administration est vide");
		}
		if(uniteAdmin.getTypeUniteAdmin().equals("") || uniteAdmin.getTypeUniteAdmin()==null)
		{
			throw new RuntimeException("Le type de l'unité d'administration est vide");
		}
		if(uniteAdmin.getSituationGeo().equals("") || uniteAdmin.getSituationGeo()==null)
		{
			throw new RuntimeException("La situation géographique de l'unité d'administration est vide");
		}
	}

	@Override
	public void validateNoneExistence(UniteAdmin ua) 
	{
		if (uniteAdminDao.findBySigle(ua.getSigle())!=null)
		{
			throw new UniteAdminException("Ce sigle ("+ ua.getSigle()+ ") est déjà attribué à une autre unité administrative");
		}
		
		if (!uniteAdminDao.findByAppellation(ua.getAppellation()).isEmpty())
		{
			throw new UniteAdminException("Ce nom ("+ ua.getAppellation()+ ") est déjà attribué à une autre unité administrative");
		}
	}
	

	
	//@Bean
	CommandLineRunner start(IUniteAdminValidation uaValidation)
	{
		return arg->
		{
			UniteAdmin ua = new UniteAdmin();
			ua.setSigle("DMP");
			ua.setAppellation("Direction Générale des Marchés Publics");
			uaValidation.validateNoneExistence(ua);
			System.out.println("=====Valide=====");
		};
	}

}
