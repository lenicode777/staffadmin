package dmp.staffadmin.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import dmp.staffadmin.metier.entities.DmdeActe;

public interface IDmdeActeDao extends JpaRepository<DmdeActe, Long> 
{

}
