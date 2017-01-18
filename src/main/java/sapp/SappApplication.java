package sapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import sapp.config.UploadProperties;

@SpringBootApplication
@EnableConfigurationProperties({UploadProperties.class})
public class SappApplication extends SpringBootServletInitializer {
	
	   @Override
	    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	        return application.sources(SappApplication.class);
	    }

	
	public static void main(String[] args) {
		SpringApplication.run(SappApplication.class, args);
	}
}
