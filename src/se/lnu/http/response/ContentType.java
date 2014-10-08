package se.lnu.http.response;

public enum ContentType {
	texthtml("text/html", "html, htm"),
	textcss("text/css", "css"),
	textjavascript("text/javascript", "js"),
	imagepng("image/png", "png"),
	imagegif("image/gif", "gif"),
	imagejpeg("image/jpeg", "jpg, jpeg"),
	applicationunknown("application/unknown", "*");
	
	private String mimeType;
	private String[] fileEndings;

	private ContentType(String mimeType, String fileEndings) {
		this.mimeType = mimeType;
		this.fileEndings = fileEndings.split(", ");
	}
	
	public String toString(){
		return mimeType;
	}

	public static ContentType getFromFileEnding(String fileEnding) {
		
		for (ContentType type : values()) {
			for (String ending : type.fileEndings) {
				if (fileEnding.equals(ending)) {
					return type;
				}
			}
		}
		
		return applicationunknown;
	}
}
