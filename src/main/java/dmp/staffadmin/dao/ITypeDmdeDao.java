package dmp.staffadmin.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import dmp.staffadmin.metier.entities.TypeDmde;

public interface ITypeDmdeDao extends JpaRepository<TypeDmde, Long> 
{

}
