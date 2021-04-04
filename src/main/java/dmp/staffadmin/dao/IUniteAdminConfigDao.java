package dmp.staffadmin.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import dmp.staffadmin.metier.entities.UniteAdminConfig;

public interface IUniteAdminConfigDao extends JpaRepository<UniteAdminConfig, Long>
{

}
