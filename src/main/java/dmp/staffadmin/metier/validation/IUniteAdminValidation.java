package dmp.staffadmin.metier.validation;

import dmp.staffadmin.metier.entities.UniteAdmin;

public interface IUniteAdminValidation extends IValidation<UniteAdmin>
{
	public void validateNoneExistence(UniteAdmin uniteAdmin, Long idUniteAdminChecked);
}
