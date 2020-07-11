package dmp.staffadmin.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import dmp.staffadmin.metier.entities.NatureActe;

public interface INatureActeDao extends JpaRepository<NatureActe, Long> 
{

}
