package dmp.staffadmin.metier.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dmp.staffadmin.dao.ITypeArchiveDao;
import dmp.staffadmin.metier.entities.TypeArchive;
import dmp.staffadmin.metier.exceptions.TypeArchiveException;

@Service
public class TypeArchiveValidation implements IValidation<TypeArchive>
{
	private @Autowired ITypeArchiveDao typeArchiveDao;

	@Override
	public boolean isValide(TypeArchive type)
	{
		return false;
	}

	@Override
	public void validate(TypeArchive type) throws RuntimeException
	{
		if (typeArchiveDao.existsByNomIgnoreCase(type.getNom())
				|| typeArchiveDao.existsByTypeArchiveDirIgnoreCase(type.getTypeArchiveDir()))
		{
			throw new TypeArchiveException("Ce type d'archive existe déjà dans le système");
		}
	}

}
