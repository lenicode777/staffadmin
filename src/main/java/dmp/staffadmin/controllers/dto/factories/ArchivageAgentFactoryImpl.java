package dmp.staffadmin.controllers.dto.factories;

import dmp.staffadmin.dao.ITypeArchiveDao;
import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.metier.entities.ArchiveAgent;
import dmp.staffadmin.metier.entities.Depart;
import dmp.staffadmin.metier.entities.TypeArchive;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class ArchivageAgentFactoryImpl implements IArchivageAgentFactory
{
	private final ITypeArchiveDao typeArchiveDao;

	public ArchivageAgentFactoryImpl(ITypeArchiveDao typeArchiveDao)
	{
		this.typeArchiveDao = typeArchiveDao;
	}

	@Override
	public ArchiveAgent createArchivageAgentFromDepart(Depart depart)
	{
		TypeArchive acteDeSortie = typeArchiveDao.findByCodeType("TYPE_ARCH7");
		Agent agent = depart.getAgent();
		ArchiveAgent archiveAgent = new ArchiveAgent();
		archiveAgent.setAgent(agent);
		archiveAgent.setDescription(depart.getMotif());
		archiveAgent.setFile(depart.getActeAdministratif());
		archiveAgent.setDateCreation(new Date());
		archiveAgent.setDateDerniereModif(new Date());
		archiveAgent.setTypeArchive(acteDeSortie);
		//archiveAgent.setIdUserCreateur();
		return archiveAgent;
	}
}
