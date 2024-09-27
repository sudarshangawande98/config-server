package ncl.configserver.c2n.dev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigServer
public class ConfigServerC2nDevApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigServerC2nDevApplication.class, args);
	}
}
