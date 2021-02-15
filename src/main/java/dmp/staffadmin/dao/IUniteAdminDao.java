package dmp.staffadmin.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.metier.entities.Post;
import dmp.staffadmin.metier.entities.TypeUniteAdmin;
import dmp.staffadmin.metier.entities.UniteAdmin;
@RepositoryRestResource
public interface IUniteAdminDao extends JpaRepository<UniteAdmin, Long> 
{
	public List<UniteAdmin> findByPostManager(Post postManager);
	public List<UniteAdmin> findByTutelleDirecte(UniteAdmin ua);
	public List<UniteAdmin> findByTutelleDirecteIdUniteAdmin(Long idTutelleDirecte);
	public List<UniteAdmin> findByTypeUniteAdmin(TypeUniteAdmin typeUA);
	public List<UniteAdmin> findByAppellation(String appelation);
	public List<UniteAdmin> findByDateCreation(Date dateCreation);
	public List<UniteAdmin> findByDateCreationBefore(Date dateCreation);
	public List<UniteAdmin> findByDateCreationAfter(Date dateCreation);
	public List<UniteAdmin> findByLevel(int level);
	public List<UniteAdmin> findByLevelGreaterThan(int level);
	public List<UniteAdmin> findByLevelLessThan(int level);
	public List<UniteAdmin> findBySigle(String sigle);
	public List<UniteAdmin> findBySigleContains(String sigle);
	public List<UniteAdmin> findBySituationGeo(String situationGeo);
	public List<UniteAdmin> findBySituationGeoContains(String situationGeo);
}
