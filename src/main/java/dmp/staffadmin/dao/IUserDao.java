package dmp.staffadmin.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import dmp.staffadmin.metier.entities.User;

public interface IUserDao extends JpaRepository<User, Long> 
{

}
