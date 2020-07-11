package dmp.staffadmin.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import dmp.staffadmin.metier.entities.Dmde;

public interface IDmdeDao extends JpaRepository<Dmde, Long> 
{

}
