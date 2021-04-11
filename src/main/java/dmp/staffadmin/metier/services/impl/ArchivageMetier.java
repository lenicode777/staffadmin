package dmp.staffadmin.metier.services.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.primefaces.shaded.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dmp.staffadmin.metier.constants.ArchivageConstants;
import dmp.staffadmin.metier.exceptions.ArchivageException;
import dmp.staffadmin.metier.services.interfaces.IArchivageMetier;
import dmp.staffadmin.utilities.FileManager;

@Service
public class ArchivageMetier implements IArchivageMetier
{
	@Override
	public String getFileExtension(MultipartFile file)
	{
		return "." + FilenameUtils.getExtension(file.getOriginalFilename());
	}

	@Override
	public void uploadFile(MultipartFile file, String destinationPath) throws RuntimeException
	{
		long fileSize = file.getSize();
		List<String> authorizedTypes = ArchivageConstants.DOCUMENT_AUTHORIZED_TYPE;

		if (fileSize > ArchivageConstants.UPLOAD_MAX_SIZE)
		{
			throw new ArchivageException("Taille de fichier > " + (ArchivageConstants.UPLOAD_MAX_SIZE / 1000) + " Mo");
		}

		// Si l'extension du fichier n'est pas contenu dans la liste des types
		// authorisés
		if (!authorizedTypes.stream().anyMatch(type -> type.equalsIgnoreCase(FileManager.getFileExtension(file))))
		{
			throw new ArchivageException("Type de fichier non pris en charge");
		}
		try
		{
			Files.write(Paths.get(destinationPath), file.getBytes());
		} catch (IOException e)
		{
			e.printStackTrace();
			throw new ArchivageException("Erreur de chargement du fichier");
		}
	}

	@Override
	public byte[] downloadFile(String filePAth)
	{
		File file = new File(filePAth);
		Path path = Paths.get(file.toURI());
		try
		{
			return Files.readAllBytes(path);
		} catch (IOException e)
		{
			e.printStackTrace();
			throw new ArchivageException("Erreur de téléchargement");
		}
	}
}
