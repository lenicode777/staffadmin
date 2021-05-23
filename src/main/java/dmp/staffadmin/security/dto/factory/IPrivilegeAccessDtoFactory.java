package dmp.staffadmin.security.dto.factory;

import java.util.List;

import dmp.staffadmin.security.dto.PrivilegeAccessDto;

public interface IPrivilegeAccessDtoFactory 
{
	public List<PrivilegeAccessDto> createListOfPrivilegeAccessDto(List<Long> allPrivilegeIds, List<Long> notRevokedPrivilegeIds);
	public PrivilegeAccessDto createPrivilegeAccessDto(Long idPrivilege, List<Long> notRevokedPrivilegeIds);
}
