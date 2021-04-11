package dmp.staffadmin.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dmp.staffadmin.metier.entities.Fonction;
import dmp.staffadmin.metier.entities.TypeUniteAdmin;

public interface IFonctionDao extends JpaRepository<Fonction, Long>
{
	public List<Fonction> findByNomFonction(String nomFonction);

	public List<Fonction> findByFonctionDeNominationTrue();

	public List<Fonction> findByFonctionDeNominationFalse();

	public List<Fonction> findByFonctionTopManagerTrue();

	public List<Fonction> findByFonctionTopManagerFalse();

	public List<Fonction> findByTypeUniteAdmin(TypeUniteAdmin typeUniteAdmin);

	public List<Fonction> findByTypeUniteAdminIdTypeUniteAdmin(Long idTypeUniteAdmin);

	public boolean existsByIdFonction(Long idFonction);
}