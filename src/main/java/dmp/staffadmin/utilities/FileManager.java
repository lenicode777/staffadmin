package dmp.staffadmin.utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.web.multipart.MultipartFile;

public class FileManager 
{
	public static String userDir = System.getProperty("user.dir");
	public static String staticDirectory = userDir + "\\src\\main\\resources\\static\\";
	
	public static String getFileExtension(MultipartFile file)
	{
		String type = file.getContentType();
		return "." + type.substring(type.lastIndexOf("/") +1 );
	}
	public static String generateFileStorePath(String entityDir, String typeFileDir, String fileName, String extension) 
	{
		
		//String uploadDir = userDir + "\\uploads\\employes\\photos\\";
		String uploadDir = userDir + "\\src\\main\\resources\\static\\staffadmin\\docs\\uploads\\" + entityDir + "\\" + typeFileDir + "\\";
		//String fileName = "photo" + emp.getMatricule() + extension;
		String path = uploadDir + fileName + extension;
		return path;
	}
	public static String generateStaticFileStorePath(String entityDir, String typeFileDir, String fileName, String extension) 
	{
		//String uploadDir = userDir + "\\uploads\\employes\\photos\\";
		String uploadDir = "\\staffadmin\\docs\\uploads\\" + entityDir + "\\" + typeFileDir + "\\";
		//String fileName = "photo" + emp.getMatricule() + extension;
		String path = uploadDir + fileName + extension;
		return path;
	}
	
	public static void store(Path path, MultipartFile file)
	{
		 try 
		 { 
			 Files.write(path, file.getBytes());
		 } 
		 catch (IOException e) 
		 { // TODO Auto-generated catch block
			 e.printStackTrace(); 
		 }		
	}
}
