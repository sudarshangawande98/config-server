package ncl.configserver.c2n.dev.config;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Scheduled task to refresh the configuration every 1 minutes
 * by triggering the bus refresh endpoint.
 */
@Component
public class ConfigRefreshTask {

    @Scheduled(fixedRate = 60000) // Run every 1 minutes (60000 milliseconds)
    public void refreshConfig() {
        try {
            // Trigger bus refresh using curl command
            ProcessBuilder processBuilder = new ProcessBuilder("curl", "-X", "POST", "http://localhost:8888/actuator/busrefresh");
            Process process = processBuilder.start();

            // Output the response of the curl command
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line); // Debugging purposes
            }

            process.waitFor(); // Wait for the process to complete
        } catch (Exception e) {
            System.err.println("Error while refreshing config: " + e.getMessage());
        }
    }
}
