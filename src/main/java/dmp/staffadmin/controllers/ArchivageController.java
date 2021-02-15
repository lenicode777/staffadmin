package dmp.staffadmin.controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.castor.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import dmp.staffadmin.dao.IAgentDao;
import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.metier.interfaces.IArchivageMetier;

@Controller
public class ArchivageController 
{
	@Autowired private IAgentDao agentDao;
	@Autowired private IArchivageMetier archivageMetier;
	
	@GetMapping(path = "/staffadmin/agents/{idAgent}/archivage")
	public String goToArchivageAgent(@PathVariable(name = "idAgent") Long idAgent, Model model)
	{
		Agent agent = agentDao.findById(idAgent).get();
		model.addAttribute("agent", agent);
		model.addAttribute("typeFile", "");
		return "archivage/agent/archivage-agent";
	}
	
	@GetMapping(path = "/staffadmin/agents/{idAgent}/{typeFichier}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
	public @ResponseBody byte[] getAgentFile(@PathVariable(name = "idAgent") Long idAgent, @PathVariable(name = "typeFichier") String typeFichier)
	{
		Agent agent = agentDao.findById(idAgent).get();
		String fileName;
		try 
		{
			Class classAgent = Class.forName("dmp.staffadmin.metier.entities.Agent");
			Method agentGetFileNameMethod = classAgent.getMethod("get"+StringUtils.capitalize(typeFichier)+"Path");
			fileName = (String) agentGetFileNameMethod.invoke(agent);
			String destinationPath = System.getProperty("user.home")+"/workspace/cefive/staffadmin/docs/uploads/agent/"+typeFichier+"/"+fileName ;
			File file = new File(destinationPath);
			Path path = Paths.get(file.toURI());
			return Files.readAllBytes(path);
			//return archivageMetier.downloadFile(destinationPath);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new RuntimeException("Erreur de téléchargement");
		}
	}
	//@GetMapping(path = "/staffadmin/agents/{idAgent}/photo", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
	public @ResponseBody byte[] getImage(@PathVariable(name = "idAgent") Long idAgent) throws IOException
	{
		Agent agent = agentDao.getOne(idAgent);
		String nomPhoto = agent.getPhotoPath();
		File file = new File(System.getProperty("user.home")+"/workspace/cefive/staffadmin/docs/uploads/agent/photo/"+nomPhoto);
		Path path = Paths.get(file.toURI());
		return Files.readAllBytes(path); 
	}
	
	@PostMapping(path = "/staffadmin/archivage/agents/{idAgent}/{typeFichier}") @Transactional
	public String uploadFile(Model model, @RequestParam MultipartFile file, @PathVariable Long idAgent, @PathVariable String typeFichier) throws IOException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		System.out.println("========================");
		System.out.println("Extension : "+archivageMetier.getFileExtension(file));
		System.out.println(idAgent);
		System.out.println("========================");
		
		long fileSize = file.getSize();
		String fileExtension = archivageMetier.getFileExtension(file);
		try
		{
			if(fileSize > 1000000) throw new RuntimeException("Taille de fichier > 1 Mo");
			if(!Arrays.asList(".jpeg", ".jpg", ".png", ".pdf").contains(fileExtension.toLowerCase())) throw new RuntimeException("Type de fichier non pris en charge");
		}
		catch (Exception e) 
		{
			model.addAttribute("errorMsg", e.getMessage());
		}
		
		Class classAgent = Class.forName("dmp.staffadmin.metier.entities.Agent");
		Method agentSetFileNameMethod = classAgent.getMethod("set"+StringUtils.capitalize(typeFichier)+"Path", String.class);
		Method agentSetFileMethod = classAgent.getMethod("set"+StringUtils.capitalize(typeFichier)+"File", MultipartFile.class);
		Method agentGetFileNameMethod = classAgent.getMethod("get"+StringUtils.capitalize(typeFichier)+"Path");
		Method agentGetFileMethod = classAgent.getMethod("get"+StringUtils.capitalize(typeFichier)+"File");
		Agent agent = agentDao.findById(idAgent).get();
		agentSetFileNameMethod.invoke(agent, typeFichier + "_" + agent.getIdAgent() + fileExtension);
		agentSetFileMethod.invoke(agent, file);
		//agent.setPhotoPath(typeFichier + "_" + idAgent + ".jpeg");
		//MultipartFile file = (MultipartFile)agentGetFileMethod.invoke(agent);
		agentDao.save(agent);
		Files.write(Paths.get(System.getProperty("user.home")+"/workspace/cefive/staffadmin/docs/uploads/agent/"+typeFichier+"/"+agentGetFileNameMethod.invoke(agent)), file.getBytes());
		model.addAttribute("agent", agent);
		return "archivage/agent/archivage-agent.html";
	}
	
	@PostMapping(path = "/staffadmin/archivage/agents/{idAgent}") @Transactional
	public String uploadFile2(Model model, @RequestParam MultipartFile file, @PathVariable Long idAgent, @RequestParam(name="typeFile") String typeFichier) throws IOException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		System.out.println("========================");
		System.out.println("Extension : "+archivageMetier.getFileExtension(file));
		System.out.println(idAgent);
		System.out.println("========================");
		
		long fileSize = file.getSize();
		String fileExtension = archivageMetier.getFileExtension(file);
		try
		{
			if(fileSize > 1000000) throw new RuntimeException("Taille de fichier > 1 Mo");
			if(!Arrays.asList(".jpeg", ".jpg", ".png", ".pdf").contains(fileExtension.toLowerCase())) throw new RuntimeException("Type de fichier non pris en charge");
		}
		catch (Exception e) 
		{
			model.addAttribute("errorMsg", e.getMessage());
		}
		
		Class classAgent = Class.forName("dmp.staffadmin.metier.entities.Agent");
		Method agentSetFileNameMethod = classAgent.getMethod("set"+StringUtils.capitalize(typeFichier)+"Path", String.class);
		Method agentSetFileMethod = classAgent.getMethod("set"+StringUtils.capitalize(typeFichier)+"File", MultipartFile.class);
		Method agentGetFileNameMethod = classAgent.getMethod("get"+StringUtils.capitalize(typeFichier)+"Path");
		Method agentGetFileMethod = classAgent.getMethod("get"+StringUtils.capitalize(typeFichier)+"File");
		Agent agent = agentDao.getOne(idAgent);
		agentSetFileNameMethod.invoke(agent, typeFichier + "_" + agent.getIdAgent() + fileExtension);
		agentSetFileMethod.invoke(agent, file);
		//agent.setPhotoPath(typeFichier + "_" + idAgent + ".jpeg");
		//MultipartFile file = (MultipartFile)agentGetFileMethod.invoke(agent);
		agentDao.save(agent);
		Files.write(Paths.get(System.getProperty("user.home")+"/workspace/cefive/staffadmin/docs/uploads/agent/"+typeFichier+"/"+agentGetFileNameMethod.invoke(agent)), file.getBytes());
		model.addAttribute("agent", agent);
		return "archivage/agent/archivage-agent.html";
	}
	
	/*
	  
	 //Exemple Load image with thymeleaf controller
	@GetMapping(
			  value = "/get-image-with-media-type",
			  produces = MediaType.IMAGE_JPEG_VALUE
			)
			public @ResponseBody byte[] getImageWithMediaType() throws IOException {
			    InputStream in = getClass()
			      .getResourceAsStream("/com/baeldung/produceimage/image.jpg");
			    //return IOUtils.toByteArray(in);
			    return null;
			}
	*/
}
