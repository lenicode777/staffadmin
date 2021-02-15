package dmp.staffadmin.security.userdetailsservice;

import dmp.staffadmin.metier.interfaces.ICrudMetier;

public interface IUserMetier extends ICrudMetier<User>
{
	public User addRoleToUser(User user, Role role);
	public User removeRoleToUser(User user, Role role);
	public User activateUser(User user);
	public User desactivateUser(User user);
	User changePassword(UserForm userForm);
	public boolean hasRole(User user, Role role);
}
