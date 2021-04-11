package dmp.staffadmin.utilities;

import org.apache.commons.lang3.StringUtils;

public class StringManipulation
{
	String toCamelCase(String text)
	{
		return StringUtils.capitalize(text);
	}
}
