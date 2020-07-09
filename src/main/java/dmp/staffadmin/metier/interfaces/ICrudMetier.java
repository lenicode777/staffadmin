package dmp.staffadmin.metier.interfaces;

import java.util.List;

public interface ICrudMetier<Entity> 
{
	public Entity save();
	public Entity update(Entity e);
	public List<Entity> findAll();
}
