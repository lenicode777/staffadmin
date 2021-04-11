package dmp.staffadmin.metier.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dmp.staffadmin.dao.IAgentDao;
import dmp.staffadmin.dao.ITypeArchiveDao;
import dmp.staffadmin.metier.entities.ArchiveAgent;
import dmp.staffadmin.metier.exceptions.ArchivageAgentException;

@Service
public class ArchiveAgentValidation implements IValidation<ArchiveAgent>
{
	private @Autowired IAgentDao agentDao;
	private @Autowired ITypeArchiveDao typeArchiveDao;

	@Override
	public boolean isValide(ArchiveAgent e)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(ArchiveAgent archive) throws ArchivageAgentException
	{
		if (!agentDao.existsById(archive.getAgent().getIdAgent()))
		{
			throw new ArchivageAgentException("Références de l'agent incorrectes");
		}

		if (!typeArchiveDao.existsById(archive.getTypeArchive().getId()))
		{
			throw new ArchivageAgentException("Type d'archive iconnue");
		}
	}

}
