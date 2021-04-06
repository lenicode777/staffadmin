package dmp.staffadmin.metier.services.interfaces;

import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.metier.entities.Post;
import dmp.staffadmin.metier.entities.UniteAdmin;

public interface IPostMetier extends ICrudMetier<Post> 
{
	public Post nommerResponsable(Post post, Agent agent);
	public Post demettreResponsable(Post post, Agent agent);
}
