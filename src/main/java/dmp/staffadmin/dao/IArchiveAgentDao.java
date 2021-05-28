package dmp.staffadmin.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dmp.staffadmin.metier.entities.ArchiveAgent;
import dmp.staffadmin.metier.entities.TypeArchive;

public interface IArchiveAgentDao extends JpaRepository<ArchiveAgent, Long>
{
	public List<ArchiveAgent> findByAgentIdAgentAndTypeArchiveId(Long idAgent, Long idTypeArchive);

	public List<ArchiveAgent> findByAgentIdAgent(Long idAgent);

	public List<ArchiveAgent> findByTypeArchiveId(Long idTypeArchive);

	public boolean existsByPath(String completePath);
	public boolean existsByTypeArchive(TypeArchive typeArchive);
	public boolean existsByTypeArchiveId(Long idTypeArchive);
}
