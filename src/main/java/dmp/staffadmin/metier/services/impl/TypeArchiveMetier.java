package dmp.staffadmin.metier.services.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dmp.staffadmin.dao.ITypeArchiveDao;
import dmp.staffadmin.metier.entities.TypeArchive;
import dmp.staffadmin.metier.services.interfaces.ITypeArchiveMetier;
import dmp.staffadmin.utilities.Constants;

@Service
@Transactional
public class TypeArchiveMetier implements ITypeArchiveMetier
{

	@Autowired
	private ITypeArchiveDao typeArchiveDao;

	@Override
	public TypeArchive save(TypeArchive typeArchive)
	{

		File typeArchiveDir = new File(Constants.AGENT_UPLOADS_DIR, typeArchive.getNom());
		if (!typeArchiveDir.exists())
		{
			typeArchiveDir.mkdir();
		}
		typeArchive.setTypeArchiveDir(typeArchiveDir.getPath());

		return typeArchiveDao.save(typeArchive);
	}

}
