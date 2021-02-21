package dmp.staffadmin.metier.interfaces;

import dmp.staffadmin.metier.entities.UniteAdmin;

public interface IUniteAdminStats 
{
	public long getNbAgents(UniteAdmin uniteAdmin);

	long getNbFilles(UniteAdmin uniteAdmin);

	long getNbGarcons(UniteAdmin uniteAdmin);

	long getNbFonctionnaires(UniteAdmin uniteAdmin);

	long getNbContractuelles(UniteAdmin uniteAdmin);
	
}
