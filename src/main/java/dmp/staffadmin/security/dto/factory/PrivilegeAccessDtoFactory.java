package dmp.staffadmin.security.dto.factory;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dmp.staffadmin.security.dao.AppPrivilegeDao;
import dmp.staffadmin.security.dto.PrivilegeAccessDto;
import dmp.staffadmin.security.model.AppPrivilege;
@Component
public class PrivilegeAccessDtoFactory implements IPrivilegeAccessDtoFactory 
{
	@Autowired private AppPrivilegeDao privilegeDao;
	@Override
	public List<PrivilegeAccessDto> createListOfPrivilegeAccessDto(List<Long> allPrivilegeIds, List<Long> notRevokedPrivilegeIds) 
	{
		return allPrivilegeIds.stream().map(id->createPrivilegeAccessDto(id, notRevokedPrivilegeIds)).collect(Collectors.toList());
	}
	@Override
	public PrivilegeAccessDto createPrivilegeAccessDto(Long idPrivilege, List<Long> notRevokedPrivilegeIds) 
	{
		AppPrivilege privilege = privilegeDao.findById(idPrivilege).get();
		
		PrivilegeAccessDto privilegeAccessDto = new PrivilegeAccessDto();
		privilegeAccessDto.setIdPrivilege(idPrivilege);
		privilegeAccessDto.setPrivilegeName(privilege.getPrivilegeName());
		boolean notRevoked = notRevokedPrivilegeIds.stream()
												   .anyMatch(notRevokedPrivilegeId->notRevokedPrivilegeId.longValue()==idPrivilege.longValue());		
		privilegeAccessDto.setNotRevoked(notRevoked);
		
		return privilegeAccessDto;
	}

}
