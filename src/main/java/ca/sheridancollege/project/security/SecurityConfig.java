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

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterchain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvc = new MvcRequestMatcher.Builder(introspector);

        return http
                .authorizeHttpRequests(authorize -> authorize
                        // Publicly accessible resources
                        .requestMatchers(mvc.pattern("/js/**")).permitAll()
                        .requestMatchers(mvc.pattern("/css/**")).permitAll()
                        .requestMatchers(mvc.pattern("/images/**")).permitAll()
                        .requestMatchers(mvc.pattern("/permission-denied")).permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()

                        // Shared access for GM and AGM
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


                        // Restricted access for GM
                        .requestMatchers(mvc.pattern("/order/**")).hasRole("GM")
                        .requestMatchers(mvc.pattern("/createOrder/**")).hasRole("GM")
                        .requestMatchers(mvc.pattern("/submitOrder")).hasRole("GM")
                        .requestMatchers(mvc.pattern("/updateOrder")).hasRole("GM")
                        .requestMatchers(mvc.pattern("/editOrder/**")).hasRole("GM")

                )

                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**"))
                        .disable()
                )
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .formLogin(form -> form.loginPage("/login").permitAll())
                .exceptionHandling(exception -> exception.accessDeniedPage("/permission-denied"))
                .logout(logout -> logout.logoutUrl("/logout").permitAll())
                .rememberMe(rememberMe -> rememberMe
                        .rememberMeCookieName("remember-me")
                        .tokenValiditySeconds(2592000)
                        .key("uniqueAndSecret")
                        .alwaysRemember(false)
                )
// Persistent only when checkbox is selected
                .sessionManagement(session -> session
                        .sessionFixation().migrateSession() // Prevent session fixation attacks
                        .maximumSessions(1) // Limit to one session per user
                        .expiredUrl("/login?expired=true") // Redirect when session expires
                )
                .build();
    }


}
