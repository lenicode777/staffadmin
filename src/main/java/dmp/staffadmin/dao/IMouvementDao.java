package dmp.staffadmin.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import dmp.staffadmin.metier.entities.Mouvement;

public interface IMouvementDao extends JpaRepository<Mouvement, Long> 
{

}
