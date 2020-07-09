package dmp.staffadmin.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import dmp.staffadmin.metier.entities.Post;

public interface IPostDao extends JpaRepository<Post, Long> 
{

}
