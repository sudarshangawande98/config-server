package ncl.configserver.c2n.dev.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        // Allow access to the H2 console without authentication
                        .requestMatchers("/h2-console/**").permitAll()

                        // Define role-based access for services and environments
                        .requestMatchers("/c2n-margin-service/dev/**").hasRole("DEV_C2N_MARGIN_SERVICE")
                        .requestMatchers("/c2n-margin-service/prod/**").hasRole("PROD_C2N_MARGIN_SERVICE")
                        .requestMatchers("/c2n-cim-service/dev/**").hasRole("DEV_C2N_CIM_SERVICE")
                        .requestMatchers("/c2n-cim-service/prod/**").hasRole("PROD_C2N_CIM_SERVICE")

                        // Any other requests must be authenticated
                        .anyRequest().authenticated()
                )
                // Enable HTTP Basic authentication
                .httpBasic(withDefaults())

                // Disable CSRF for non-production simplicity (enable in production)
                .csrf(csrf -> csrf.disable())

                // Disable X-Frame-Options to allow H2 console to work in a frame
                .headers(headers -> headers.frameOptions().disable());

        return http.build();
    }

    @Bean
    public UserDetailsService jdbcUserDetailsManager(DataSource dataSource) {
        // Use JdbcUserDetailsManager to manage users in the database via JDBC
        return new JdbcUserDetailsManager(dataSource);
    }
}
