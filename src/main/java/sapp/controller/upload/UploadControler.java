package sapp.controller.upload;

import sapp.config.UploadProperties;
import sapp.model.User;
import sapp.service.UserService;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.net.URLConnection;
import java.nio.file.AccessDeniedException;
import java.util.Locale;

@Controller
public class UploadControler {
	private final Resource picturesDir;
	private final Resource anonymousPicture;
	private final MessageSource messageSource;
	
	@Autowired UserService userService;

    @Autowired
    public UploadControler(UploadProperties uploadProperties,
                                   MessageSource messageSource) {
        picturesDir = uploadProperties.getPicturesUploadPath();
        anonymousPicture = uploadProperties.getAnonymousPicture();
        this.messageSource = messageSource;
    }

	@RequestMapping(value = "/profile/uploadAvatar", params = {"upload"},  method = RequestMethod.POST)
	public String onUpload(MultipartFile file, RedirectAttributes redirectAttrs, Locale locale) throws IOException {
		System.out.println("upload start");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth instanceof AnonymousAuthenticationToken) {
			throw new AccessDeniedException("");
		}
		System.out.println("upload auth ok");
		if (file.isEmpty() || !isImage(file)) {
			redirectAttrs.addFlashAttribute("error", messageSource.getMessage("upload.bad.file.type", null, locale));
			return "redirect:/profile/edit";
		}		
		String path = copyFileToPictures(file);
		System.out.println("after copy");
		System.out.println("find  " + auth.getName());
		User user = userService.findByUsername(auth.getName());
		System.out.println("found: " + user);
		
		user.setAvatarPath(path);
		userService.saveOrUpdate(user);
		
		return "redirect:/profile/show";
	}

	// get uploaded picture
	@RequestMapping(value = "/useravatar")
	public void getUploadedPicture(HttpServletResponse response) throws IOException {
		Resource picturePath;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			User modelUser = userService.findByUsername(auth.getName());
			if( modelUser.getAvatarPath() ==  null || modelUser.getAvatarPath().isEmpty()){
				picturePath = anonymousPicture;
			}else{
				System.out.println("PICTURE PATH:??");
				picturePath = (new DefaultResourceLoader()).getResource("file:./" + modelUser.getAvatarPath());
				System.out.println(picturePath);
			}
		}else{
			picturePath = anonymousPicture;//getPicturePath();
		}
        response.setHeader("Content-Type", URLConnection.guessContentTypeFromName(picturePath.getFilename()));
		IOUtils.copy(picturePath.getInputStream(), response.getOutputStream());
	}

	// ---------------- helpers
	private String copyFileToPictures(MultipartFile file) throws IOException {
		System.out.println("inside copy");
		String fileExtension = getFileExtension(file.getOriginalFilename());
		System.out.println("after get extension: " + fileExtension);
		System.out.println("picturesDir: "+ picturesDir);
		File tempFile = File.createTempFile("pic", fileExtension, picturesDir.getFile());
		System.out.println("after createtempfile: " + tempFile);
		try (InputStream in = file.getInputStream(); OutputStream out = new FileOutputStream(tempFile)) {
			System.out.println("...copy");
			IOUtils.copy(in, out);
			System.out.println("...after copy");
		}
		System.out.println("returning path: " + (new FileSystemResource(tempFile).getPath()));
		return new FileSystemResource(tempFile).getPath();
	}

	private boolean isImage(MultipartFile file) {
		return file.getContentType().startsWith("image");
	}

	private static String getFileExtension(String name) {
		return name.substring(name.lastIndexOf("."));
	}

   @ExceptionHandler(IOException.class)
	    public ModelAndView handleIOException(Locale locale) {
	        ModelAndView modelAndView = new ModelAndView("profileedit");
	        modelAndView.addObject("error", messageSource.getMessage("upload.io.exception", null, locale));
	        //modelAndView.addObject("profileForm", userProfileSession.toForm());
	        return modelAndView;
	    }
	
	
	@RequestMapping("uploadError")
	public ModelAndView onUploadError(Locale locale) {
		ModelAndView modelAndView = new ModelAndView("profile/profilePage");
		modelAndView.addObject("error", messageSource.getMessage("upload.file.too.big", null, locale));
		//modelAndView.addObject("profileForm", userProfileSession.toForm());
		return modelAndView;
	}
	
}