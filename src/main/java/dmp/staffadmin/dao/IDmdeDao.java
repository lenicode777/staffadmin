package dmp.staffadmin.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import dmp.staffadmin.metier.entities.Demande;

public interface IDmdeDao extends JpaRepository<Demande, Long> 
{

}
