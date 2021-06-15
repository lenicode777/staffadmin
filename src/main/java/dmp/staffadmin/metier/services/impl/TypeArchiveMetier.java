package dmp.staffadmin.metier.services.impl;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dmp.staffadmin.dao.IArchiveAgentDao;
import dmp.staffadmin.dao.ITypeArchiveDao;
import dmp.staffadmin.metier.constants.ArchivageConstants;
import dmp.staffadmin.metier.entities.TypeArchive;
import dmp.staffadmin.metier.exceptions.TypeArchiveException;
import dmp.staffadmin.metier.services.interfaces.ITypeArchiveMetier;
import dmp.staffadmin.metier.validation.TypeArchiveValidation;

@Service
@Transactional
public class TypeArchiveMetier implements ITypeArchiveMetier
{

	@Autowired
	private ITypeArchiveDao typeArchiveDao;
	@Autowired private IArchiveAgentDao archiveAgentDao;

	private @Autowired TypeArchiveValidation typeArchiveValidation;

	@Override
	public TypeArchive save(TypeArchive typeArchive) throws TypeArchiveException
	{
		StringBuilder sb = new StringBuilder();
		for (String str : typeArchive.getNom().split("[^A-Za-z0-9À-ÿ]"))
		{
			sb.append(StringUtils.capitalize(str));
		}
		String dir = sb.toString();

		System.out.println("DIR = " + dir);

		typeArchive.setTypeArchiveDir(dir);
		typeArchiveValidation.validate(typeArchive);
		File typeArchiveDirectory = new File(ArchivageConstants.AGENT_UPLOADS_DIR, typeArchive.getTypeArchiveDir());
		if (!typeArchiveDirectory.exists())
		{
			typeArchiveDirectory.mkdir();
		}
		typeArchive.setTypeArchivePath(typeArchiveDirectory.getPath());
		typeArchive.setCodeType(generateTypeCode());
		return typeArchiveDao.save(typeArchive);
	}

	@Override
	public boolean isRemovable(TypeArchive typeArchive) 
	{
		return !archiveAgentDao.existsByTypeArchive(typeArchive);
	}
	
	@Override
	public boolean isRemovable(Long  idTypeArchive) 
	{
		return !archiveAgentDao.existsByTypeArchiveId(idTypeArchive);
	}

	private String generateTypeCode()
	{
		return ArchivageConstants.CODE_TYPE_ARCHIVE+ (typeArchiveDao.count()+1);
	}
}
