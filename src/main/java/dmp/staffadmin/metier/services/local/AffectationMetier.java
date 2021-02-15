package dmp.staffadmin.metier.services.local;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dmp.staffadmin.dao.IAffectationDao;
import dmp.staffadmin.metier.entities.Affectation;
import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.metier.entities.UniteAdmin;
import dmp.staffadmin.metier.interfaces.IAffectationMetier;
import dmp.staffadmin.metier.interfaces.ICrudMetier;

@Service @Transactional
public class AffectationMetier implements IAffectationMetier, ICrudMetier<Affectation>
{
	private IAffectationDao affectationDao;
	
	public AffectationMetier(IAffectationDao affectationDao) 
	{
		this.affectationDao = affectationDao;
	}

	@Override
	public Affectation save(Affectation affectation)
	{
		return affectationDao.save(affectation);
	}

	@Override
	public Affectation update(Affectation affectation) 
	{
		return affectationDao.save(affectation);
	}

	@Override
	public Affectation update(Long idAffection, Affectation affectation) 
	{
		affectation.setId(idAffection);
		return affectationDao.save(affectation);
	}

	@Override
	public List<Affectation> findAll() 
	{
		return affectationDao.findAll();
	}

	@Override
	public List<Affectation> getAffectationByAgentIdAgent(Long idAgent) 
	{
		return affectationDao.findByAgentIdAgent(idAgent);
	}

	@Override
	public List<Affectation> getAffectationByUaArrivee(UniteAdmin ua) 
	{
		return affectationDao.findByUaArriveeIdUniteAdmin(ua.getIdUniteAdmin());
	}

	@Override
	public List<Affectation> getAffectationByUaArriveeAndVueFalse(UniteAdmin ua)
	{
		return affectationDao.findByUaArriveeAndVueFalse(ua);
	}

	@Override
	public List<Affectation> getAffectationByUaDepart(UniteAdmin ua) 
	{
		return extracted(ua);
	}

	private List<Affectation> extracted(UniteAdmin ua) {
		return affectationDao.findByUaDepartIdUniteAdmin(ua.getIdUniteAdmin());
	}

	@Override
	public List<Affectation> getAffectationByUaDepartAndVueFalse(UniteAdmin ua) 
	{
		return affectationDao.findByUaDepartAndVueFalse(ua);
	}

	@Override
	public void setAffectationVue(Affectation affectation) 
	{
		affectation.setVue(true);
	}

}
