package br.tech.oe.plan.config;

import br.tech.oe.plan.service.impl.AuthServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    @Value("${server.servlet.session.cookie.name}")
    private String sessionCookieName;

    @Value("${br.tech.oe.plan.application.webapp}")
    private String allowedOrigin;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors
                        .configurationSource(corsConfigurationSource())
                )
                .authorizeHttpRequests(authorize -> authorize
                        // Options HTTP
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Public routes
                        .requestMatchers(HttpMethod.GET, "/api/v1/ping", "/api/v1/").permitAll()

                        // Auth routes
                        .requestMatchers(
                                "/api/v1/auth/login",
                                "/api/v1/auth/register",
                                "/api/v1/auth/me",
                                "/api/v1/auth/logout"
                        ).permitAll()

                        // User routes (admin only)
                        .requestMatchers("/api/v1/users/**").hasRole("ADMIN")

                        // Task routes
                        .requestMatchers(HttpMethod.GET, "/api/v1/tasks/*").hasAnyRole("ADMIN", "DIRECTOR", "COORDINATOR", "TEACHER")
                        .requestMatchers(HttpMethod.GET, "/api/v1/tasks/{uuid}").hasAnyRole("ADMIN", "DIRECTOR", "COORDINATOR", "TEACHER")
                        .requestMatchers(HttpMethod.POST, "/api/v1/tasks").hasAnyRole("ADMIN", "DIRECTOR", "COORDINATOR")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/tasks/{uuid}").hasAnyRole("ADMIN", "DIRECTOR", "COORDINATOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/tasks/{uuid}").hasAnyRole("ADMIN", "DIRECTOR", "COORDINATOR")

                        // Task comment routes
                        .requestMatchers(HttpMethod.GET, "/api/v1/tasks/{uuid}/comments").hasAnyRole("ADMIN", "DIRECTOR", "COORDINATOR", "TEACHER")
                        .requestMatchers(HttpMethod.GET, "/api/v1/tasks/{uuid}/comments/{commentUuid}").hasAnyRole("ADMIN", "DIRECTOR", "COORDINATOR", "TEACHER")
                        .requestMatchers(HttpMethod.POST, "/api/v1/tasks/{uuid}/comments").hasAnyRole("ADMIN", "DIRECTOR", "COORDINATOR", "TEACHER")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/tasks/{uuid}/comments/{commentUuid}").hasAnyRole("ADMIN", "DIRECTOR", "COORDINATOR", "TEACHER")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/tasks/{uuid}/comments/{commentUuid}").hasAnyRole("ADMIN", "DIRECTOR", "COORDINATOR", "TEACHER")

                        // Group routes
                        .requestMatchers(HttpMethod.GET, "/api/v1/groups/*").hasAnyRole("ADMIN", "DIRECTOR", "COORDINATOR", "TEACHER")
                        .requestMatchers(HttpMethod.POST, "/api/v1/groups").hasAnyRole("ADMIN", "DIRECTOR", "COORDINATOR")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/groups/*").hasAnyRole("ADMIN", "DIRECTOR", "COORDINATOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/groups/*").hasAnyRole("ADMIN", "DIRECTOR", "COORDINATOR")

                        // TODO(fix): something is wrong here
                        .anyRequest().permitAll()
                )
                .logout(logout -> logout
                        .deleteCookies(sessionCookieName)
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                )
                .securityContext((context) -> context
                        .securityContextRepository(httpSessionSecurityContextRepository())
                        .requireExplicitSave(true)
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true)
                )
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthServiceImpl authService,
            PasswordEncoder passwordEncoder
    ) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(authService);
        provider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(provider);
    }

    @Bean
    public HttpSessionSecurityContextRepository httpSessionSecurityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedHeaders(List.of("Content-Type"));
        config.setAllowedOrigins(List.of(allowedOrigin));
        config.setAllowedMethods(List.of("GET", "POST", "PATCH", "DELETE", "OPTIONS"));
        config.setExposedHeaders(List.of("Set-Cookie"));
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
