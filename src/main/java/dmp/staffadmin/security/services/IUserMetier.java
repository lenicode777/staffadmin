package dmp.staffadmin.security.services;

import dmp.staffadmin.metier.services.interfaces.ICrudMetier;
import dmp.staffadmin.security.dto.ChangePasswordDto;
import dmp.staffadmin.security.model.AppRole;
import dmp.staffadmin.security.model.AppUser;
//@Deprecated
public interface IUserMetier extends ICrudMetier<AppUser>
{
	public AppUser addRoleToUser(AppUser user, AppRole role);
	public AppUser removeRoleToUser(AppUser user, AppRole role);
	public AppUser activateUser(AppUser user);
	public AppUser desactivateUser(AppUser user);
	AppUser changePassword(ChangePasswordDto userForm);
	public boolean hasRole(AppUser user, AppRole role);
}
