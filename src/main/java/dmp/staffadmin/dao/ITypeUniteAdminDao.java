package dmp.staffadmin.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dmp.staffadmin.metier.entities.TypeUniteAdmin;

public interface ITypeUniteAdminDao extends JpaRepository<TypeUniteAdmin, Long> 
{
	public List<TypeUniteAdmin> findByAdministrativeLevel(int administrativeLevel);
	public List<TypeUniteAdmin> findByAdministrativeLevelGreaterThan(int administrativeLevel);
	public List<TypeUniteAdmin> findByAdministrativeLevelLessThan(int administrativeLevel);
	public TypeUniteAdmin findByNomTypeUniteAdmin(String TypeUniteAdmin);
	public List<TypeUniteAdmin> findByNomTypeUniteAdminContains(String TypeUniteAdmin);
	
	
}
