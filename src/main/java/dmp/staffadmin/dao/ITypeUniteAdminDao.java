package dmp.staffadmin.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import dmp.staffadmin.metier.entities.TypeUniteAdmin;

public interface ITypeUniteAdminDao extends JpaRepository<TypeUniteAdmin, Long> 
{

}
