package sapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

@ConfigurationProperties(prefix = "upload")
public class UploadProperties {
	private Resource picturesUploadPath;
	private Resource anonymousPicture;

	public Resource getAnonymousPicture() {
		return anonymousPicture;
	}

	public void setAnonymousPicture(String anonymousPicture) {
    this.anonymousPicture = new DefaultResourceLoader().getResource(anonymousPicture);
  }

	public Resource getPicturesUploadPath() {
		return picturesUploadPath;
	}

	public void setPicturesUploadPath(String picturesUploadPath) {
		this.picturesUploadPath = new DefaultResourceLoader().getResource(picturesUploadPath);
	}
}