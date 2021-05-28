package dmp.staffadmin.controllers;

import java.util.ArrayList;
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
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dmp.staffadmin.controllers.dto.SelectRoleDto;
import dmp.staffadmin.dao.IAgentDao;
import dmp.staffadmin.dao.IUniteAdminDao;
import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.metier.entities.UniteAdmin;
import dmp.staffadmin.metier.enumeration.ErrorMsgEnum;
import dmp.staffadmin.security.aspects.AuthoritiesDtoAnnotation;
import dmp.staffadmin.security.dao.AppPrivilegeDao;
import dmp.staffadmin.security.dao.AppRoleDao;
import dmp.staffadmin.security.dao.AppUserDao;
import dmp.staffadmin.security.dto.PrivilegeAccessDto;
import dmp.staffadmin.security.dto.RolePrivilegeAssignationDto;
import dmp.staffadmin.security.dto.UserAccessDto;
import dmp.staffadmin.security.dto.factory.IUserAccessDtoFactory;
import dmp.staffadmin.security.dto.factory.UserAccessDtoFactory;
import dmp.staffadmin.security.dto.services.IUserAccessDtoManager;
import dmp.staffadmin.security.dto.services.UserAccessDtoManager;
import dmp.staffadmin.security.model.AppPrivilege;
import dmp.staffadmin.security.model.AppRole;
import dmp.staffadmin.security.model.AppUser;
import dmp.staffadmin.security.services.IUserAuthoritiesDetailsService;
import dmp.staffadmin.security.services.UserAuthoritiesDetailsService;
import dmp.staffadmin.security.utilities.ISecurityContextManager;

@Controller
@RequestMapping(path = "/staffadmin/administration")
public class AdministrationController
{
	@Autowired
	private IAgentDao agentDao;

	@Autowired
	private AppUserDao userDao;

	@Autowired
	private AppRoleDao roleDao;
	
	@Autowired private IUserAccessDtoFactory userAccessDtoFactory;
	@Autowired private IUserAccessDtoManager userAccessDtoManager;

	@Autowired
	private IUniteAdminDao uniteAdminDao;
	@Autowired private IUserAuthoritiesDetailsService userAuthoritiesDetailsService;
	@Autowired private ISecurityContextManager securityContextManager;

	@Autowired private AppPrivilegeDao privilegeDao;

	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor)
	{
		Set<Object> seen = ConcurrentHashMap.newKeySet();
		return t -> seen.add(keyExtractor.apply(t));
	}

	// @PreAuthorize("hasRole('SAF')")
	@GetMapping(path = "/gestion-roles/form")
	@AuthoritiesDtoAnnotation
	public String goToGestionRolesForm(Model model, HttpServletRequest request,
			@RequestParam(defaultValue = "") String matricule)
	{
		Agent agent = null;
		if (!matricule.equals(""))
		{
			if (agentDao.existsByMatricule(matricule))
			{
				agent = agentDao.findByMatricule(matricule);
				//agent.setUser(userDao.findByAgentIdAgent(agent.getIdAgent()).get());
				
				UniteAdmin userTutelleDirecte = agent.getTutelleDirecte();

				List<UniteAdmin> visibilitePossible = Stream
						.concat(userTutelleDirecte.getPatrentsStream(), userTutelleDirecte.getSubAdminStream())
						.filter(distinctByKey(UniteAdmin::getIdUniteAdmin))
						.sorted(Comparator.comparingInt(UniteAdmin::getLevel))
						.collect(Collectors.toList());
				
				List<AppPrivilege> usersPrivileges = userAuthoritiesDetailsService.getPrivileges(agent.getUser().getUsername());
				List<AppPrivilege> privileges = userAuthoritiesDetailsService.getPrivilegesOfRoles(agent.getUser().getRoles());
				
				UserAccessDto userAccessDto = userAccessDtoFactory.createUserAccessDto(agent, privileges); //new UserAccessDto();
				
				//userAccessDto = userAccessDtoManager.setGlobalsAccessParams(userAccessDto);
						
				model.addAttribute("agent", agent);
				model.addAttribute("privileges", privileges);
				model.addAttribute("userAccessDto", userAccessDto);
				model.addAttribute("visibilitePossible", visibilitePossible);
				model.addAttribute("roles", roleDao.findAll());
			} 
			else
			{
				model.addAttribute(ErrorMsgEnum.GLOBAL_ERROR_MSG.toString(), "Matricule introuvable");
			}
		}
		return "administration/gestion-roles/frm-gestion-roles";
	}

	@GetMapping(path = "/gestion-roles/setRoles")
	@Transactional
	@AuthoritiesDtoAnnotation
	@Deprecated
	public String setRoles0(HttpServletRequest request, Model model, @RequestParam(defaultValue = "0") Long idUser,
			@RequestParam(defaultValue = "") String idRoles, @RequestParam(defaultValue = "0") Long visibilite,
			boolean active)
	{
		AppUser user = userDao.findById(idUser).get();
		 List<AppRole> roles = Arrays.asList(idRoles.replace(" ","").split(",")).stream()
		 .map(id -> new AppRole(Long.parseLong(id), null, null, null, null)).collect(Collectors.toList());

		user.setRoles(roles);
		user.setIdUaChampVisuel(visibilite);
		user.setActive(active);
		user.getAgent().setActive(active);

		userDao.save(user);
		agentDao.save(user.getAgent());

		return "redirect:/staffadmin/administration/gestion-roles/form?matricule=" + user.getAgent().getMatricule();
	}
	
	@PostMapping(path = "/gestion-roles/setRoles")
	@Transactional
	@AuthoritiesDtoAnnotation
	public String setRoles(HttpServletRequest request, Model model, 
							@RequestParam(defaultValue = "0") Long idUser,
							@RequestParam(defaultValue = "") String idRoles, 
							@RequestParam(defaultValue = "0") Long idUaChampVisuel,
							boolean active,
							@RequestParam(defaultValue = "") String notRevokedPrivilegeIds,
							@RequestParam(defaultValue = "") String privilegeIds)
	{
		AppUser user = userDao.findById(idUser).get();
		
		System.out.println("idUser = " + idUser);
		System.out.println("idRoles = " + idRoles);
		System.out.println("idUaChampVisuel = " + idUaChampVisuel);
		System.out.println("active = " + active);
		System.out.println("notRevokedPrivilegeIds = " + notRevokedPrivilegeIds);
		System.out.println("privilegeIds = " + privilegeIds);
		UserAccessDto userAccessDto = userAccessDtoFactory.createUserAccessDto(idUser, idRoles, idUaChampVisuel, active, notRevokedPrivilegeIds, privilegeIds);
		userAccessDtoManager.setGlobalsAccessParams(userAccessDto);
		return "redirect:/staffadmin/administration/gestion-roles/form?matricule=" + user.getAgent().getMatricule();
	}

	@GetMapping(path = "/list-users")
	@AuthoritiesDtoAnnotation
	public String goToListUsers(Model model, HttpServletRequest request)
	{
		List<AppUser> listUsers = userDao.findAll();
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
	
	@GetMapping(path = "/select-role-form")
	@AuthoritiesDtoAnnotation
	public String goToSelectRoleForm(HttpServletRequest request, Model model)
	{
		SelectRoleDto selectRoleDto = new SelectRoleDto();
		String username = request.getUserPrincipal().getName();
		AppUser user = userDao.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("Utilisateur introuvable"));
		model.addAttribute("selectRoleDto", selectRoleDto);
		model.addAttribute("roles", user.getRoles());
		return "administration/gestion-roles/frm-select-role";
	}
	
	@PostMapping(path = "/select-role")
	public String setRoleAsDefault(HttpServletRequest request, Model model, @ModelAttribute SelectRoleDto roleDto, SecurityContextHolder securityContextHolder)
	{
		String username = request.getUserPrincipal().getName();
		AppUser user = userDao.findByUsername(username).get();
		
		user.setDefaultRoleId(roleDto.getSelectedRoleId());
		userDao.save(user);
		securityContextManager.refreshSecurityContext(user, securityContextHolder);
		return "redirect:/";
	}
	
	@GetMapping(path = "/role-privileges-assignation/form")
	@AuthoritiesDtoAnnotation
	public String goToRolePrivilegesAssignation(Model model, HttpServletRequest request)
	{
		model.addAttribute("roles", roleDao.findAll());
		model.addAttribute("privileges", privilegeDao.findAll());
		
		RolePrivilegeAssignationDto privilegeAssignationDto = new RolePrivilegeAssignationDto();
		model.addAttribute("privilegeAssignationDto", privilegeAssignationDto);
		
		return "administration/gestion-roles/role-privileges-assignation/frm-role-privileges-assignation";
	}
	
	@PostMapping(path = "/role-privileges-assignation/save")
	@AuthoritiesDtoAnnotation
	public String saveRolePrivilegesAssignation(Model model, 
			HttpServletRequest request,
			@RequestParam long roleId,
			@RequestParam String privilegeIds)
	{
		System.out.println("========RoleIds========" + roleId);
		System.out.println("========PrivilegeIds========" +privilegeIds);
		
		AppRole role = roleDao.findById(roleId).get();
		
		List<AppPrivilege> privileges = Arrays.asList(privilegeIds.split(","))
										.stream()
										.map(id->privilegeDao.findById(Long.parseLong(id)).get())
										.collect(Collectors.toList());
		
		role.setPrivileges(privileges);
		roleDao.save(role);
		
		return "redirect:/staffadmin/administration/role-privileges-assignation/form";
	}
	
}
