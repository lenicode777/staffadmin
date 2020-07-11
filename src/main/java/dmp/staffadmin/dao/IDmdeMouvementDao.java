package dmp.staffadmin.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import dmp.staffadmin.metier.entities.DmdeMouvement;

public interface IDmdeMouvementDao extends JpaRepository<DmdeMouvement, Long> 
{

}
