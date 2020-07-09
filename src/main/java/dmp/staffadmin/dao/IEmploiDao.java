package dmp.staffadmin.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dmp.staffadmin.metier.entities.Emploi;

public interface IEmploiDao extends JpaRepository<Emploi, Long>
{
	public List<Emploi> findByNomEmploi(String nomEmploi);
}
