package dmp.staffadmin.metier.services.interfaces;

import javax.servlet.http.HttpServletResponse;

import dmp.staffadmin.metier.entities.ArchiveAgent;

public interface IArchivageAgentMetier
{
	public ArchiveAgent uploadArchiveAgent(ArchiveAgent archive);

	public String getArchiveAgentCompletePath(ArchiveAgent archive);

	public void downloadArchiveAgent(Long idArchiveAgent, HttpServletResponse response);

	public void deleteArchiveAgent(Long idArchiveAgent);
}
