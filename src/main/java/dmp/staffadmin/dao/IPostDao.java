package dmp.staffadmin.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.metier.entities.Post;
import dmp.staffadmin.metier.entities.UniteAdmin;

public interface IPostDao extends JpaRepository<Post, Long> 
{
	public Post findByAgent(Agent agent);
	public List<Post> findByUniteAdmin(UniteAdmin uniteAdmin);
}
