package dmp.staffadmin.metier.services.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.servlet.http.HttpServletResponse;

import org.primefaces.shaded.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dmp.staffadmin.dao.IAgentDao;
import dmp.staffadmin.dao.IArchiveAgentDao;
import dmp.staffadmin.dao.ITypeArchiveDao;
import dmp.staffadmin.metier.constants.ArchivageConstants;
import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.metier.entities.ArchiveAgent;
import dmp.staffadmin.metier.entities.TypeArchive;
import dmp.staffadmin.metier.exceptions.ArchivageAgentException;
import dmp.staffadmin.metier.services.interfaces.IArchivageAgentMetier;
import dmp.staffadmin.metier.services.interfaces.IArchivageMetier;
import dmp.staffadmin.metier.validation.IValidation;
import dmp.staffadmin.utilities.FileManager;

@Service
@Transactional
public class ArchivageAgentMetier implements IArchivageAgentMetier
{
	private @Autowired IArchivageMetier archivageMetier;
	private @Autowired IArchiveAgentDao archiveAgentDao;
	private @Autowired IAgentDao agentDao;
	private @Autowired ITypeArchiveDao typeArchiveDao;
	private @Autowired IValidation<ArchiveAgent> archiveValidation;

	@Override
	public void uploadArchiveAgent(ArchiveAgent archiveAgent) throws ArchivageAgentException
	{
		archiveValidation.validate(archiveAgent);
		Agent agent = agentDao.findById(archiveAgent.getAgent().getIdAgent()).get();
		TypeArchive typeArchive = typeArchiveDao.findById(archiveAgent.getTypeArchive().getId()).get();
		archiveAgent.setAgent(agent);
		archiveAgent.setTypeArchive(typeArchive);
		String completePath = getArchiveAgentCompletePath(archiveAgent);

		archiveAgent.setPath(completePath);

		archivageMetier.uploadFile(archiveAgent.getFile(), completePath);
		archiveAgentDao.save(archiveAgent);
	}

	@Override
	public void downloadArchiveAgent(Long idArchiveAgent, HttpServletResponse response) throws ArchivageAgentException
	{
		if (!archiveAgentDao.existsById(idArchiveAgent))
		{
			throw new ArchivageAgentException("Archive introuvable");
		}
		ArchiveAgent archiveAgent = archiveAgentDao.findById(idArchiveAgent).get();

		response.setHeader("Content-Disposition",
				"attachement; filename=" + FilenameUtils.getName(archiveAgent.getPath()));
		response.setHeader("Content-Transfert-Encoding", "binary");
		try
		{
			BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
			FileInputStream fis = new FileInputStream(archiveAgent.getPath());
			int len;
			byte[] buf = new byte[1024];
			while ((len = fis.read(buf)) > 0)
			{
				bos.write(buf, 0, len);
			}
			bos.close();
			response.flushBuffer();
		} catch (Exception e)
		{
			e.printStackTrace();
			throw new ArchivageAgentException("Une erreur est survenue pendant le téléchargement");

		}

	}

	@Override
	public String getArchiveAgentCompletePath(ArchiveAgent archiveAgent)
	{
		String fileExtension = FileManager.getFileExtension(archiveAgent.getFile());

		String typeArchiveDir = archiveAgent.getTypeArchive().getTypeArchiveDir();
		String completePath = ArchivageConstants.AGENT_UPLOADS_DIR + "\\" + typeArchiveDir + "\\" + typeArchiveDir + "_"
				+ archiveAgent.getAgent().getIdAgent() + fileExtension;
		int i = 2;
		while (archiveAgentDao.existsByPath(completePath))
		{
			completePath = ArchivageConstants.AGENT_UPLOADS_DIR + "\\" + typeArchiveDir + "\\" + typeArchiveDir + "_"
					+ archiveAgent.getAgent().getIdAgent() + "(" + i + ")" + fileExtension;
			i = i + 1;
		}

		return completePath;
	}

	@Override
	public void deleteArchiveAgent(Long idArchiveAgent)
	{
		ArchiveAgent archiveAgent = archiveAgentDao.findById(idArchiveAgent).get();
		File file = new File(archiveAgent.getPath());
		file.delete();
		archiveAgentDao.deleteById(idArchiveAgent);
	}
}
