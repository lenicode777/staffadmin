package dmp.staffadmin.metier.services.interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface IArchivageMetier 
{
	public void uploadFile(MultipartFile file, String destinationPath);
	public byte[] downloadFile(String filePAth);
	public String getFileExtension(MultipartFile file);
}
