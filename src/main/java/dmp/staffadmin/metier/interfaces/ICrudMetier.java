package dmp.staffadmin.metier.interfaces;

import java.util.List;

public interface ICrudMetier<Entity> 
{
	public Entity save(Entity e);
	public Entity update(Entity e);
	public Entity update(Long entityId, Entity entityBody);
	public List<Entity> findAll();
}
