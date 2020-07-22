package dmp.staffadmin.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.metier.entities.TypeUniteAdmin;
import dmp.staffadmin.metier.entities.UniteAdmin;

public interface IUniteAdminDao extends JpaRepository<UniteAdmin, Long> 
{
	public List<UniteAdmin> findByResponsable(Agent responsable);
	public List<UniteAdmin> findByTutelleDirecte(UniteAdmin ua);
	public List<UniteAdmin> findByTypeUniteAdmin(TypeUniteAdmin typeUA);
	public List<UniteAdmin> findByAppelation(String appelation);
	public List<UniteAdmin> findByDateCreation(Date dateCreation);
	public List<UniteAdmin> findByDateCreationBefore(Date dateCreation);
	public List<UniteAdmin> findByDateCreationAfter(Date dateCreation);
	public List<UniteAdmin> findByLevel(int level);
	public List<UniteAdmin> findBySigle(String sigle);
	public List<UniteAdmin> findBySituationGeo(String situationGeo);
	public List<UniteAdmin> findBySituationGeoContains(String situationGeo);
}
