package dmp.staffadmin.metier.services.interfaces;

import java.util.List;

import dmp.staffadmin.metier.entities.Affectation;
import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.metier.entities.UniteAdmin;

public interface IAffectationMetier extends ICrudMetier<Affectation>
{
	public List<Affectation> getAffectationByAgentIdAgent(Long idAgent);
	public List<Affectation> getAffectationByUaArrivee(UniteAdmin ua);
	//public List<Affectation> getAffectationByUaArriveeAndVueFalse(UniteAdmin ua);
	public List<Affectation> getAffectationByUaDepart(UniteAdmin ua);
	//public List<Affectation> getAffectationByUaDepartAndVueFalse(UniteAdmin ua);
}