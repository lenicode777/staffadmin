package dmp.staffadmin.controllers;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dmp.staffadmin.dao.IAgentDao;
import dmp.staffadmin.dao.IUniteAdminDao;
import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.metier.entities.UniteAdmin;
import dmp.staffadmin.metier.enumeration.ErrorMsgEnum;
import dmp.staffadmin.security.userdetailsservice.IRoleDao;
import dmp.staffadmin.security.userdetailsservice.IUserDao;
import dmp.staffadmin.security.userdetailsservice.Role;
import dmp.staffadmin.security.userdetailsservice.User;

@Controller
public class AdministrationController
{
	@Autowired
	private IAgentDao agentDao;

	@Autowired
	private IUserDao userDao;

	@Autowired
	private IRoleDao roleDao;

	@Autowired
	private IUniteAdminDao uniteAdminDao;

	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor)
	{
		Set<Object> seen = ConcurrentHashMap.newKeySet();
		return t -> seen.add(keyExtractor.apply(t));
	}

	// @PreAuthorize("hasRole('SAF')")
	@GetMapping(path = "/staffadmin/administration/gestion-roles/form")
	public String goToGestionRolesForm(Model model, HttpServletRequest request,
			@RequestParam(defaultValue = "") String matricule)
	{
		Agent agent = null;
		if (!matricule.equals(""))
		{
			if (agentDao.existsByMatricule(matricule))
			{
				agent = agentDao.findByMatricule(matricule);
				UniteAdmin userTutelleDirecte = agent.getTutelleDirecte();
				System.out.println("AdministrationController-->goToGestionRolesForm L55");
				List<UniteAdmin> tutelleDirecteParents = userTutelleDirecte.getPatrentsStream()
						.collect(Collectors.toList());
				System.out.println("AdministrationController-->goToGestionRolesForm L58");
				List<UniteAdmin> tutelleDirecteChildren = userTutelleDirecte.getSubAdminStream()
						.collect(Collectors.toList());

				System.out.println("AdministrationController-->goToGestionRolesForm L62");
				List<UniteAdmin> visibilitePossible = Stream
						.concat(tutelleDirecteParents.stream(), tutelleDirecteChildren.stream())
						.collect(Collectors.toList());
				System.out.println("AdministrationController-->goToGestionRolesForm L65");

				visibilitePossible = visibilitePossible.stream().filter(distinctByKey(UniteAdmin::getIdUniteAdmin))
						.sorted(Comparator.comparingInt(UniteAdmin::getLevel)).collect(Collectors.toList());

				model.addAttribute("visibilitePossible", visibilitePossible);

				System.out.println("==============visibilitePossible=============");
				visibilitePossible.forEach(ua -> System.out.println(ua));
				model.addAttribute("agent", agent);
			} else
			{
				model.addAttribute(ErrorMsgEnum.GLOBAL_ERROR_MSG.toString(), "Matricule introuvable");
			}
		}

		model.addAttribute("roles", roleDao.findAll());
		return "administration/gestion-roles/frm-gestion-roles";
	}

	@GetMapping(path = "/staffadmin/administration/gestion-roles/setRoles")
	@Transactional
	public String setRoles(Model model, @RequestParam(defaultValue = "0") Long idUser,
			@RequestParam(defaultValue = "") String idRoles, @RequestParam(defaultValue = "0") Long visibilite,
			boolean active)
	{
		User user = userDao.findById(idUser).get();
		List<Role> roles = Arrays.asList(idRoles.replace(" ", "").split(",")).stream()
				.map(id -> new Role(Long.parseLong(id), null, null)).collect(Collectors.toList());

		user.setRoles(roles);
		user.setIdUaChampVisuel(visibilite);
		user.setActive(active);
		user.getAgent().setActive(active);

		userDao.save(user);
		agentDao.save(user.getAgent());

		return "redirect:/staffadmin/administration/gestion-roles/form?matricule=" + user.getAgent().getMatricule();
	}

	@GetMapping(path = "/staffadmin/administration/list-users")
	public String goToListUsers(Model model)
	{
		List<User> listUsers = userDao.findAll();
		listUsers.forEach(user ->
		{
			if (user.getIdUaChampVisuel() != null)
			{
				user.setUaChampVisuel(uniteAdminDao.findById(user.getIdUaChampVisuel()).get());
			}
		});
		model.addAttribute("listUsers", listUsers);
		model.addAttribute("uaDao", uniteAdminDao);

		return "administration/list-users/list-users";
	}
}
