package dmp.staffadmin.metier.services.interfaces;

import dmp.staffadmin.metier.entities.TypeArchive;

public interface ITypeArchiveMetier
{
	public TypeArchive save(TypeArchive typeArchive);
	public boolean isRemovable(TypeArchive typeArchive);
	public boolean isRemovable(Long idTypeArchive);
}
