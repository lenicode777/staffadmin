package dmp.staffadmin.metier.enumeration;

public enum ErrorMsgEnum 
{
	GLOBAL_ERROR_MSG("globalErrorMsg"),
	ERROR_MSG("errorMsg");

	ErrorMsgEnum (String errorMsg)
	{
		this.errorMsg = errorMsg;
	}
	private String errorMsg;
	
	public String toString() {return this.errorMsg;}
}
