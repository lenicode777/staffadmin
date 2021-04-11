package dmp.staffadmin.metier.enumeration;

public enum ErrorMsgEnum
{
	GLOBAL_ERROR_MSG("globalErrorMsg"), ERROR_MSG("errorMsg"), TYPE_ARCHIVE_ERROR_MSG("typeArchiveErrorMsg");

	// ERROR_MSG("errorMsg"),
	// ERROR_MSG("errorMsg"),

	ErrorMsgEnum(String errorMsg)
	{
		this.errorMsg = errorMsg;
	}

	private String errorMsg;

	@Override
	public String toString()
	{
		return this.errorMsg;
	}
}
