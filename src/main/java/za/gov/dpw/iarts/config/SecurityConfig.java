package za.gov.dpw.iarts.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import za.gov.dpw.iarts.security.JwtAuthenticationFilter;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    @Value("${app.cors.allowed-origins}")
    private String allowedOrigins;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint((request, response, ex) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                        .accessDeniedHandler((request, response, ex) -> response.sendError(HttpServletResponse.SC_FORBIDDEN)))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/login", "/actuator/health").permitAll()
                        .requestMatchers("/api/auth/register").hasRole("ADMIN")
                        .requestMatchers("/api/audit/**").hasAnyRole("AUDITOR", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/asset-approvals/**").hasAnyRole("ASSET_MANAGEMENT", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/assets/**").hasAnyRole("ICT_STOREROOM", "ASSET_MANAGEMENT", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/stock/**").hasAnyRole("ICT_STOREROOM", "ASSET_MANAGEMENT", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/registers/**").hasAnyRole("ICT_STOREROOM", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/registers/**").hasAnyRole("ICT_STOREROOM", "ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/registers/**").hasAnyRole("ICT_STOREROOM", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/availability-requests").hasAnyRole("ICT_STOREROOM", "ASSET_MANAGEMENT", "ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/availability-requests/*/status").hasAnyRole("ICT_STOREROOM", "ASSET_MANAGEMENT", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/intra-requests").hasAnyRole("TECHNICIAN", "ICT_STOREROOM", "ASSET_MANAGEMENT", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/intra-requests/reference/**").hasAnyRole("TECHNICIAN", "ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/intra-requests/*/status").hasAnyRole("TECHNICIAN", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/requests/**").hasAnyRole("END_USER", "TECHNICIAN", "ICT_STOREROOM", "ASSET_MANAGEMENT", "ICT_MANAGEMENT", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/requests/*/approve").hasAnyRole("ASSET_MANAGEMENT", "ICT_MANAGEMENT", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/damage").hasAnyRole("TECHNICIAN", "ASSET_MANAGEMENT", "ADMIN")
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(allowedOrigins.split(",")));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
