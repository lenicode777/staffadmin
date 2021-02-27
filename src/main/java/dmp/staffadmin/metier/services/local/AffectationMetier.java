package dmp.staffadmin.metier.services.local;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dmp.staffadmin.dao.IAffectationDao;
import dmp.staffadmin.dao.IAgentDao;
import dmp.staffadmin.dao.IUniteAdminDao;
import dmp.staffadmin.metier.entities.Affectation;
import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.metier.entities.UniteAdmin;
import dmp.staffadmin.metier.exceptions.AffectationException;
import dmp.staffadmin.metier.interfaces.IAffectationMetier;
import dmp.staffadmin.metier.interfaces.ICrudMetier;
import dmp.staffadmin.metier.validation.IValidation;

@Service @Transactional
public class AffectationMetier implements IAffectationMetier, ICrudMetier<Affectation>
{
	@Autowired private IAffectationDao affectationDao;
	@Autowired private IAgentDao agentDao;
	@Autowired private IUniteAdminDao uniteAdminDao;
	@Autowired private IValidation<Affectation> affectationValidation;
	
	public AffectationMetier(IAffectationDao affectationDao) 
	{
		this.affectationDao = affectationDao;
	}

	@Override
	public Affectation save(Affectation affectation) throws AffectationException
	{
		Agent agentAAffecter = agentDao.findById(affectation.getAgent().getIdAgent()).get();
		UniteAdmin uaArrivee = uniteAdminDao.findById(affectation.getUaArrivee().getIdUniteAdmin()).get();
		UniteAdmin uaDepart = agentAAffecter.getTutelleDirecte();

		agentAAffecter.setTutelleDirecte(uaArrivee);

		affectation.setUaDepart(uaDepart);
		affectation.setAgent(agentAAffecter);
		affectation.setUaArrivee(uaArrivee);
		
		affectationValidation.validate(affectation);
		
		agentAAffecter = agentDao.save(agentAAffecter);
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
