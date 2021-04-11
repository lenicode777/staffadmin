package dmp.staffadmin.utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.primefaces.shaded.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import dmp.staffadmin.metier.constants.ArchivageConstants;

public class FileManager
{
	public static String userDir = System.getProperty("user.dir");
	public static String uploadDir = ArchivageConstants.UPLOADS_DIR;
	public static String staticDirectory = userDir + "\\src\\main\\resources\\static\\";

	public static String getFileExtension(MultipartFile file)
	{
		return "." + FilenameUtils.getExtension(file.getOriginalFilename());
	}

	public static String generateFileStorePath(String entityDir, String typeFileDir, String fileName, String extension)
	{
		String path = uploadDir + "/" + entityDir + "/" + typeFileDir + "/" + fileName + extension;
		return path;
	}

	public static void store(Path path, MultipartFile file)
	{
		try
		{
			Files.write(path, file.getBytes());
		} catch (IOException e)
		{ // TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
