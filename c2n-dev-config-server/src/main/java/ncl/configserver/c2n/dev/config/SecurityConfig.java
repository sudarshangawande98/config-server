package ncl.configserver.c2n.dev.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@PropertySource(value = "classpath:security-config.yml", factory = YamlPropertySourceFactory.class)
public class SecurityConfig {

    private final SecurityRolesProperties securityRolesProperties;

    // Inject SecurityRolesProperties to use role mappings from YAML
    public SecurityConfig(SecurityRolesProperties securityRolesProperties) {
        this.securityRolesProperties = securityRolesProperties;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> {
                    securityRolesProperties.getRoles().forEach(roleConfig -> {
                        String path = roleConfig.get("path");
                        String role = roleConfig.get("role");
                        requests.requestMatchers(path).hasAuthority(role);  // Ensure hasAuthority is used
                    });

                    // Permit access to the H2 console without authentication
                    requests.requestMatchers("/h2-console/**").permitAll();

                    // Permit access to the bus-refresh actuator endpoint without authentication
                    requests.requestMatchers("/actuator/**").permitAll();

                    // Secure other endpoints by requiring authentication
                    requests.anyRequest().authenticated();
                })
                .httpBasic()
                .and()
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions().disable());

        return http.build();
    }

    @Bean
    public UserDetailsService jdbcUserDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }
}
