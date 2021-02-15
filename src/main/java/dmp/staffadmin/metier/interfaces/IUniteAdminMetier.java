package dmp.staffadmin.metier.interfaces;

import java.util.List;

import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.metier.entities.UniteAdmin;
import dmp.staffadmin.metier.interfaces.ICrudMetier;

public interface IUniteAdminMetier extends ICrudMetier<UniteAdmin>
{
	public UniteAdmin setSubAdminTree(UniteAdmin uniteAdmin);
	public List<Agent> getAllPersonnel(UniteAdmin ua);
}
