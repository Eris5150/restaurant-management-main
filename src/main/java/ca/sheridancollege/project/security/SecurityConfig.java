package ca.sheridancollege.project.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

/**
 * Spring Security configuration:
 * - Defines URL authorization rules.
 * - Configures form login, logout, remember-me, and session policies.
 * - Exposes a BCrypt password encoder bean.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    /** BCrypt encoder for hashing user passwords. */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Main security filter chain defining access rules and security features.
     */
    @Bean
    SecurityFilterChain filterchain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvc = new MvcRequestMatcher.Builder(introspector);

        return http
                .authorizeHttpRequests(authorize -> authorize
                        // Public resources and endpoints (no authentication required)
                        .requestMatchers(mvc.pattern("/js/**")).permitAll()
                        .requestMatchers(mvc.pattern("/css/**")).permitAll()
                        .requestMatchers(mvc.pattern("/images/**")).permitAll()
                        .requestMatchers(mvc.pattern("/permission-denied")).permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()

                        // Shared access: GM and AGM roles
                        .requestMatchers(mvc.pattern("/menu")).hasAnyRole("GM", "AGM")
                        .requestMatchers(mvc.pattern("/insertMenuItem")).hasAnyRole("GM", "AGM")
                        .requestMatchers(mvc.pattern("/editMenuItemById/**")).hasAnyRole("GM", "AGM")
                        .requestMatchers(mvc.pattern("/deleteMenuItemById/**")).hasAnyRole("GM", "AGM")
                        .requestMatchers(mvc.pattern("/inventory/**")).hasAnyRole("GM", "AGM")
                        .requestMatchers(mvc.pattern("/insertInventoryItem/")).hasAnyRole("GM", "AGM")
                        .requestMatchers(mvc.pattern("/deleteInventoryItemById/**")).hasAnyRole("GM", "AGM")
                        .requestMatchers(mvc.pattern("/editInventoryItemById/**")).hasAnyRole("GM", "AGM")
                        .requestMatchers(mvc.pattern("/reviews/**")).hasAnyRole("GM", "AGM")
                        .requestMatchers(mvc.pattern("/index/")).hasAnyRole("GM", "AGM")
                        .requestMatchers(mvc.pattern("/")).hasAnyRole("GM", "AGM")
                        .requestMatchers(mvc.pattern("/insertInventoryItem")).hasAnyRole("GM", "AGM")
                        .requestMatchers(mvc.pattern("/editInventoryItemById/**")).hasAnyRole("GM", "AGM")

                        // Restricted to GM role only
                        .requestMatchers(mvc.pattern("/order/**")).hasRole("GM")
                        .requestMatchers(mvc.pattern("/createOrder/**")).hasRole("GM")
                        .requestMatchers(mvc.pattern("/submitOrder")).hasRole("GM")
                        .requestMatchers(mvc.pattern("/updateOrder")).hasRole("GM")
                        .requestMatchers(mvc.pattern("/editOrder/**")).hasRole("GM")

                )

                // CSRF disabled for H2 console; globally disabled to simplify demo flows
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**"))
                        .disable()
                )

                // Allow H2 console to render in a frame (dev/testing use)
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))

                // Custom login page
                .formLogin(form -> form.loginPage("/login").permitAll())

                // Friendly access denied page
                .exceptionHandling(exception -> exception.accessDeniedPage("/permission-denied"))

                // Standard logout endpoint
                .logout(logout -> logout.logoutUrl("/logout").permitAll())

                // Persistent login (remember-me) configuration
                .rememberMe(rememberMe -> rememberMe
                        .rememberMeCookieName("remember-me")
                        .tokenValiditySeconds(2592000)   // 30 days
                        .key("uniqueAndSecret")          // Token hashing key
                        .alwaysRemember(false)           // Only when checkbox is selected
                )

                // Session management: harden against fixation, limit concurrent sessions
                .sessionManagement(session -> session
                        .sessionFixation().migrateSession()   // Rotate session ID after auth
                        .maximumSessions(1)                   // Single concurrent session
                        .expiredUrl("/login?expired=true")    // Redirect on session expiry
                )
                .build();
    }
}
