package dmp.staffadmin.metier.services.interfaces;

import java.util.List;

import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.metier.entities.Fonction;
import dmp.staffadmin.metier.entities.UniteAdmin;
import dmp.staffadmin.metier.services.interfaces.ICrudMetier;

public interface IUniteAdminMetier extends ICrudMetier<UniteAdmin>, IUniteAdminStats
{
	public UniteAdmin nommerResponsable(UniteAdmin uniteAdmin, Agent agentANommer, Fonction fonctionDeNomination);

	public UniteAdmin setSubAdminTree(UniteAdmin uniteAdmin);
	public List<Agent> getAllPersonnel(UniteAdmin ua);
}
