package dmp.staffadmin.metier.validation;

import java.util.Date;

import dmp.staffadmin.dao.IDepartDao;
import dmp.staffadmin.metier.entities.Agent;
import org.springframework.stereotype.Service;

import dmp.staffadmin.dao.IAgentDao;
import dmp.staffadmin.dao.ITypeDepartDao;
import dmp.staffadmin.metier.entities.Depart;
import dmp.staffadmin.metier.exceptions.DepartException;

@Service
public class DepartValidation implements IDepartValidation 
{
	private final IAgentDao agentDao;
	private final ITypeDepartDao typeDepartDao;
	private final IDepartDao departDao;

	public DepartValidation(IAgentDao agentDao, ITypeDepartDao typeDepartDao, IDepartDao departDao) {
		this.agentDao = agentDao;
		this.typeDepartDao = typeDepartDao;
		this.departDao = departDao;
	}

	@Override
	public boolean isValide(Depart e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Depart depart) throws RuntimeException 
	{
		Agent agent = depart.getAgent();
		if(depart==null)
		{
			throw new DepartException("Informations sur le depart non parvenues");
		}
		else
		{
			if(depart.getAgent() == null)
			{
				throw new DepartException("Informations sur l'agent non parvenues");
			}
			else
			{
				if(depart.getAgent().getIdAgent()==null ||
						!agentDao.existsById(depart.getAgent().getIdAgent()))
				{
					throw new DepartException("Références invalides à l'agent");
				}

				if(departDao.existsByAgentIdAgent(depart.getAgent().getIdAgent()))
				{
					if(departDao.findByAgent(agent).stream().anyMatch(depart1 -> depart1.getDateDepart().equals(depart.getDateDepart())))
					{
						throw new DepartException("Un départ à déjà été enregistré à cette date pour cet agent");
					}
				}
			}
			
			if(depart.getTypeDepart()==null)
			{
				throw new DepartException("Type de départ non fournie");
			}
			else
			{
				if(!typeDepartDao.existsById(depart.getTypeDepart().getId()) || depart.getTypeDepart().getId() == null)
				{
					throw new DepartException("Type de départ non fournie");
				}
			}
			
			if(depart.getDateDepart()==null)
			{
				throw new DepartException("Date de départ non fournie");
			}
			else
			{
				if(depart.getDateDepart().after(new Date()))
				{
					throw new DepartException("La date de départ ne peut être ultérieur à aujourd'hui");
				}
			}
		}
	}

}
