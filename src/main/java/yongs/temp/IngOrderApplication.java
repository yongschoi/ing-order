package yongs.temp;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class IngOrderApplication {
	@Value("${spring.application.name}")
	private String appName;
	public static void main(String[] args) {
		SpringApplication.run(IngOrderApplication.class, args);
	}
	@GetMapping("/")
    public String home() throws Exception {
        return "Hello, " + appName + " " + LocalDateTime.now();
    }
}
