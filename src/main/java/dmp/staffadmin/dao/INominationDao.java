package dmp.staffadmin.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import dmp.staffadmin.metier.entities.Nomination;

public interface INominationDao extends JpaRepository<Nomination, Long> 
{

}
