package dmp.staffadmin.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import dmp.staffadmin.metier.entities.Recrutement;

public interface IRecrutementDao extends JpaRepository<Recrutement, Long> 
{

}
