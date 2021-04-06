package dmp.staffadmin.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import dmp.staffadmin.metier.entities.ArchiveAgent;

public interface IArchiveAgentDao extends JpaRepository<ArchiveAgent, Long>
{

}
