package dmp.staffadmin.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dmp.staffadmin.dao.IAgentDao;
import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.metier.enumeration.ErrorMsgEnum;
import dmp.staffadmin.security.userdetailsservice.IRoleDao;
import dmp.staffadmin.security.userdetailsservice.IUserDao;
import dmp.staffadmin.security.userdetailsservice.Role;
import dmp.staffadmin.security.userdetailsservice.User;

@Controller
public class GestionRolesController
{
	@Autowired
	private IAgentDao agentDao;

	@Autowired
	private IUserDao userDao;

	@Autowired
	private IRoleDao roleDao;

	@PreAuthorize("hasRole('SAF')")
	@GetMapping(path = "/staffadmin/gestion-roles/form")
	public String goToGestionRolesForm(Model model, @RequestParam(defaultValue = "") String matricule)
	{
		Agent agent = null;
		if (!matricule.equals(""))
		{
			if (agentDao.existsByMatricule(matricule))
			{
				agent = agentDao.findByMatricule(matricule);
				model.addAttribute("agent", agent);
			} else
			{
				System.out.println("ERROR MSG = " + ErrorMsgEnum.GLOBAL_ERROR_MSG.toString());
				model.addAttribute(ErrorMsgEnum.GLOBAL_ERROR_MSG.toString(), "Matricule introuvable");
			}
		}
		model.addAttribute("roles", roleDao.findAll());
		return "administration/gestion-roles/frm-gestion-roles";
	}

	@GetMapping(path = "/staffadmin/gestion-roles/setRoles")
	public String setRoles(Model model, @RequestParam(defaultValue = "0") Long idUser,
			@RequestParam(defaultValue = "") String idRoles)
	{
		User user = userDao.findById(idUser).get();
		List<Role> roles = Arrays.asList(idRoles.replace(" ", "").split(",")).stream()
				.map(id -> new Role(Long.parseLong(id), null, null)).collect(Collectors.toList());

		user.setRoles(roles);

		userDao.save(user);

		return "redirect:/staffadmin/gestion-roles/form?matricule=" + user.getAgent().getMatricule();
	}
}
