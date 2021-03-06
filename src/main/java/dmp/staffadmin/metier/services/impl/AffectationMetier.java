package dmp.staffadmin.metier.services.impl;

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
import dmp.staffadmin.metier.enumeration.TypeUniteAdminEnum;
import dmp.staffadmin.metier.exceptions.AffectationException;
import dmp.staffadmin.metier.services.interfaces.IAffectationMetier;
import dmp.staffadmin.metier.services.interfaces.ICrudMetier;
import dmp.staffadmin.metier.services.interfaces.IUniteAdminConfigService;
import dmp.staffadmin.metier.validation.IValidation;

@Service @Transactional
public class AffectationMetier implements IAffectationMetier, ICrudMetier<Affectation>
{
	@Autowired private IAffectationDao affectationDao;
	@Autowired private IAgentDao agentDao;
	@Autowired private IUniteAdminDao uniteAdminDao;
	@Autowired private IValidation<Affectation> affectationValidation;
	@Autowired private IUniteAdminConfigService uniteAdminConfigService;
	
	public AffectationMetier(IAffectationDao affectationDao) 
	{
		this.affectationDao = affectationDao;
	}

	@Override
	public Affectation save(Affectation affectation) throws AffectationException
	{
		UniteAdmin DGMP = uniteAdminConfigService.getUniteAdminMere();
		UniteAdmin cabinetDGMP = uniteAdminConfigService.getCabinetUniteAdminMere();
		
		Agent agentAAffecter = agentDao.findById(affectation.getAgent().getIdAgent()).get();
		UniteAdmin uaArrivee = uniteAdminDao.findById(affectation.getUaArrivee().getIdUniteAdmin()).get();
		UniteAdmin uaDepart = agentAAffecter.getTutelleDirecte();
		if(uaDepart.getIdUniteAdmin()!=uaArrivee.getIdUniteAdmin())
		{
			if(uaArrivee.getIdUniteAdmin() == DGMP.getIdUniteAdmin())
			{
				uaArrivee=cabinetDGMP;
			}
			agentAAffecter.setTutelleDirecte(uaArrivee);
			agentAAffecter.setAttenteAffectation(false);
	
			affectation.setUaDepart(uaDepart);
			affectation.setAgent(agentAAffecter);
			affectation.setUaArrivee(uaArrivee);
			
			affectationValidation.validate(affectation);
			
			agentAAffecter = agentDao.save(agentAAffecter);
			return affectationDao.save(affectation);
		}
		return null;
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
	public List<Affectation> getAffectationByUaDepart(UniteAdmin ua) 
	{
		return extracted(ua);
	}

	private List<Affectation> extracted(UniteAdmin ua) {
		return affectationDao.findByUaDepartIdUniteAdmin(ua.getIdUniteAdmin());
	}
}
