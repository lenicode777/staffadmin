package dmp.staffadmin.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import dmp.staffadmin.metier.entities.UniteAdmin;

public interface IUniteAdminDao extends JpaRepository<UniteAdmin, Long> 
{

}
