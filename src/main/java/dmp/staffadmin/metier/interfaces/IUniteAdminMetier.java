package dmp.staffadmin.metier.interfaces;

import dmp.staffadmin.metier.entities.UniteAdmin;
import dmp.staffadmin.metier.interfaces.ICrudMetier;

public interface IUniteAdminMetier extends ICrudMetier<UniteAdmin>
{
	public UniteAdmin setSubAdminTree(UniteAdmin uniteAdmin);
}
