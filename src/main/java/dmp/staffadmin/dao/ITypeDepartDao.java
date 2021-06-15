package dmp.staffadmin.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dmp.staffadmin.metier.entities.TypeDepart;

public interface ITypeDepartDao extends JpaRepository<TypeDepart, Long> 
{
	TypeDepart findByNomType(String nomType);
	boolean existsByNomType(String nomType);
}
