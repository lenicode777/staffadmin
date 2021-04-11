package dmp.staffadmin.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import dmp.staffadmin.metier.entities.TypeArchive;

public interface ITypeArchiveDao extends JpaRepository<TypeArchive, Long>
{
	public TypeArchive findByNomIgnoreCase(String nom);

	public boolean existsByNomIgnoreCase(String nom);

	public boolean existsByTypeArchiveDirIgnoreCase(String typeArchiveDir);
}
