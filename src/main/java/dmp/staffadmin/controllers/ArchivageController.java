package dmp.staffadmin.controllers;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import dmp.staffadmin.dao.IAgentDao;
import dmp.staffadmin.dao.ITypeArchiveDao;
import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.metier.services.interfaces.IArchivageMetier;

@Controller
public class ArchivageController
{
	@Autowired
	private IAgentDao agentDao;
	@Autowired
	private IArchivageMetier archivageMetier;
	private @Autowired ITypeArchiveDao typeArchiveDao;

	// @GetMapping(path = "/staffadmin/agents/{idAgent}/photo", produces =
	// {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
	public @ResponseBody byte[] getImage(@PathVariable(name = "idAgent") Long idAgent) throws IOException
	{
		Agent agent = agentDao.getOne(idAgent);
		String nomPhoto = agent.getNomPhoto();
		File file = new File(
				System.getProperty("user.home") + "/workspace/cefive/staffadmin/docs/uploads/agent/photo/" + nomPhoto);
		Path path = Paths.get(file.toURI());
		return Files.readAllBytes(path);
	}

	@PostMapping(path = "/staffadmin/archivage/agents/{idAgent}")
	@Transactional
	public String uploadFile2(Model model, @RequestParam MultipartFile file, @PathVariable Long idAgent,
			@RequestParam(name = "0") String typeFichier)
			throws IOException, ClassNotFoundException, NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		System.out.println("Extension : " + archivageMetier.getFileExtension(file));

		long fileSize = file.getSize();
		String fileExtension = archivageMetier.getFileExtension(file);
		try
		{
			if (fileSize > 1000000)
				throw new RuntimeException("Taille de fichier > 1 Mo");
			if (!Arrays.asList(".jpeg", ".jpg", ".png", ".pdf").contains(fileExtension.toLowerCase()))
				throw new RuntimeException("Type de fichier non pris en charge");
		} catch (Exception e)
		{
			model.addAttribute("errorMsg", e.getMessage());
		}

		Class classAgent = Class.forName("dmp.staffadmin.metier.entities.Agent");
		Method agentSetFileNameMethod = classAgent.getMethod("setNom" + StringUtils.capitalize(typeFichier),
				String.class);
		Method agentSetFileMethod = classAgent.getMethod("set" + StringUtils.capitalize(typeFichier) + "File",
				MultipartFile.class);
		Method agentGetFileNameMethod = classAgent.getMethod("getNom" + StringUtils.capitalize(typeFichier));
		Method agentGetFileMethod = classAgent.getMethod("get" + StringUtils.capitalize(typeFichier) + "File");
		Agent agent = agentDao.getOne(idAgent);
		agentSetFileNameMethod.invoke(agent, typeFichier + "_" + agent.getIdAgent() + fileExtension);
		agentSetFileMethod.invoke(agent, file);
		// agent.setPhotoPath(typeFichier + "_" + idAgent + ".jpeg");
		// MultipartFile file = (MultipartFile)agentGetFileMethod.invoke(agent);
		agentDao.save(agent);
		Files.write(Paths.get(System.getProperty("user.home") + "/workspace/cefive/staffadmin/docs/uploads/agent/"
				+ typeFichier + "/" + agentGetFileNameMethod.invoke(agent)), file.getBytes());
		model.addAttribute("agent", agent);
		return "archivage/agent/archivage-agent.html";
	}

	/*
	 * 
	 * //Exemple Load image with thymeleaf controller
	 * 
	 * @GetMapping( value = "/get-image-with-media-type", produces =
	 * MediaType.IMAGE_JPEG_VALUE ) public @ResponseBody byte[]
	 * getImageWithMediaType() throws IOException { InputStream in = getClass()
	 * .getResourceAsStream("/com/baeldung/produceimage/image.jpg"); //return
	 * IOUtils.toByteArray(in); return null; }
	 */
}
