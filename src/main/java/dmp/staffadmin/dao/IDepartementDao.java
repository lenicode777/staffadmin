package dmp.staffadmin.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import dmp.staffadmin.metier.entities.Departement;

public interface IDepartementDao extends JpaRepository<Departement, Long>
{

}
