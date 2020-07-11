package dmp.staffadmin.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import dmp.staffadmin.metier.entities.NatureMouvement;

public interface INatureMouvementDao extends JpaRepository<NatureMouvement, Long> 
{

}
