package dmp.staffadmin.metier.services.impl;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.metier.services.interfaces.IArchivageMetier;

@Service
public class ArchivageMetier implements IArchivageMetier 
{
	@Override
	public String getFileExtension(MultipartFile file)
	{
		String type = file.getContentType();
		return "." + type.substring(type.lastIndexOf("/") +1 );
	}
	
	@Override
	public void uploadFile(MultipartFile file, String destinationPath) 
	{
		long fileSize = file.getSize();
		
		if(fileSize>1000)
		{
			throw new RuntimeException("Taille de fichier > 1 Mo");
		}
		try 
		{
			Files.write(Paths.get(System.getProperty("user.home")+destinationPath), file.getBytes());
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			throw new RuntimeException("Erreur de chargement du fichier");
		}
	}

	@Override
	public byte[] downloadFile(String filePAth) 
	{
		File file = new File(System.getProperty("user.home")+filePAth);
		Path path = Paths.get(file.toURI());
		try 
		{
			return Files.readAllBytes(path);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			throw new RuntimeException("Erreur de téléchargement");
			
		}
	}

}
