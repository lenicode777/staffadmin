package dmp.staffadmin.metier.validation;

public interface IValidation<Entity> 
{
	public boolean isValide(Entity e);
	public void validate(Entity e);
}
