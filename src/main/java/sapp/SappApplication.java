package sapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import sapp.config.UploadProperties;

@SpringBootApplication
@EnableConfigurationProperties({UploadProperties.class})
public class SappApplication {

	public static void main(String[] args) {
		SpringApplication.run(SappApplication.class, args);
	}
}
