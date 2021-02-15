package dmp.staffadmin.metier.validation;

import org.springframework.stereotype.Service;

import dmp.staffadmin.metier.entities.UniteAdmin;
@Service
public class UniteAdminValidation implements IUniteAdminValidation
{
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

}
