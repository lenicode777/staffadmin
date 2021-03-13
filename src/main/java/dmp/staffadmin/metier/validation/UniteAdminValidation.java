package dmp.staffadmin.metier.validation;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import dmp.staffadmin.dao.IUniteAdminDao;
import dmp.staffadmin.metier.entities.UniteAdmin;
import dmp.staffadmin.metier.enumeration.UniteAdminEnum;
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
	public void validate(UniteAdmin uniteAdmin) throws UniteAdminException
	{
		if(uniteAdmin.getDateCreation().after(new Date())) throw new UniteAdminException("La date de création de l'unité d'administration ne peut être ultérieur à aujourd'hui");
		if(uniteAdmin.getAppellation().equals("") || uniteAdmin.getAppellation()==null)
		{
			throw new UniteAdminException("Le nom de l'unité d'administration ne peut être vide");
		}
		if(uniteAdmin.getSigle().equals("") || uniteAdmin.getSigle()==null)
		{
			throw new UniteAdminException("Le sigle de l'unité d'administration ne peut être vide");
		}
		if( uniteAdmin.getTypeUniteAdmin()==null)
		{
			throw new UniteAdminException("Le type de l'unité d'administration ne peut être vide");
		}
		if(uniteAdmin.getSituationGeo().equals("") || uniteAdmin.getSituationGeo()==null)
		{
			throw new RuntimeException("La situation géographique de l'unité d'administration ne peut être vide");
		}
	}

	@Override
	public void validateNoneExistence(UniteAdmin ua, Long idUniteAdminChecked) throws UniteAdminException
	{
		if(idUniteAdminChecked==null) //Si idUniteAdminChecked est null alors il s'agit de la validation d'une nouvelle UA
		{
			
			if (uniteAdminDao.existsBySigle(ua.getSigle()))
			{
				throw new UniteAdminException("Ce sigle ("+ ua.getSigle()+ ") est déjà attribué à une autre unité administrative");
			}
		}
		else //Sinon c'est un update et doit s'assurer que l'élément retrouvé ne soit pas celui que nous voulons modifier
		{
			
			if (uniteAdminDao.existsBySigle(ua.getSigle()) && 
				idUniteAdminChecked.longValue() != uniteAdminDao.findBySigle(ua.getSigle()).getIdUniteAdmin().longValue())
			{
				throw new UniteAdminException("Ce sigle ("+ ua.getSigle()+ ") est déjà attribué à une autre unité administrative");
			}
		}
		
		if(idUniteAdminChecked==null) //Si idUniteAdminChecked est null alors il s'agit de la validation d'une nouvelle UA
		{
			if (uniteAdminDao.existsByAppellation(ua.getAppellation()))
			{
				throw new UniteAdminException("Ce nom ("+ ua.getAppellation()+ ") est déjà attribué à une autre unité administrative");
			}
		}
		else
		{
			if (uniteAdminDao.existsByAppellation(ua.getAppellation()) && 
				idUniteAdminChecked.longValue()!=uniteAdminDao.findByAppellation(ua.getAppellation()).getIdUniteAdmin().longValue())
			{
				throw new UniteAdminException("Ce nom ("+ ua.getAppellation()+ ") est déjà attribué à une autre unité administrative");
			}
		}
	}
	

	
	//@Bean
	CommandLineRunner start(IUniteAdminValidation uaValidation)
	{
		return arg->
		{
			UniteAdmin ua = new UniteAdmin();
			ua.setSigle(UniteAdminEnum.DGMP.toString());
			ua.setAppellation("Direction Générale des Marchés Publics");
			uaValidation.validateNoneExistence(ua, null);
			System.out.println("=====Valide=====");
		};
	}

}
