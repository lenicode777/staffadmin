package dmp.staffadmin.metier.constants;

import java.util.Arrays;
import java.util.List;

public class ArchivageConstants
{
	public static final String UPLOADS_DIR = System.getProperty("user.home")
			+ "\\workspace\\dgmp\\staffadmin\\docs\\uploads";

	public static final String AGENT_UPLOADS_DIR = System.getProperty("user.home")
			+ "\\workspace\\dgmp\\staffadmin\\docs\\uploads\\agent";
	public static final long UPLOAD_MAX_SIZE = 1048576;
	public static final List<String> PHOTO_AUTHORIZED_TYPE = Arrays.asList(".jpeg", ".jpg", ".png");
	public static final List<String> DOCUMENT_AUTHORIZED_TYPE = Arrays.asList(".jpeg", ".jpg", ".png", ".pdf", ".doc",
			".docx", ".docs", ".vnd.openxmlformats-officedocument.wordprocessingml.document");
}
