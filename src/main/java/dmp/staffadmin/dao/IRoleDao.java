package dmp.staffadmin.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import dmp.staffadmin.metier.entities.Role;

public interface IRoleDao extends JpaRepository<Role, Long> 
{

}
