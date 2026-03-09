package unicam.ids2526.gal.progetto_hackhub_gal.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /// Da "riattivare" dopo aver implementato il login
    /*@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Disabilita CSRF per permettere alla console H2 di inviare comandi
                .csrf(AbstractHttpConfigurer::disable)
                // Permetti l'accesso alla console H2 senza autenticazione
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll()
                        .anyRequest().authenticated()
                )
                // Permetti i frame (necessari per l'interfaccia della console H2)
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        return http.build();
    }*/

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. Disabilita CSRF: fondamentale per le POST di Postman
                .csrf(AbstractHttpConfigurer::disable)

                // 2. Configura i permessi
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll() // Sblocca H2
                        .requestMatchers("/inviti/**").permitAll()    // Sblocca i tuoi test
                        .anyRequest().permitAll()                     // Sblocca tutto il resto
                )

                // 3. Sblocca i Frame: H2 Console li usa per l'interfaccia grafica
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        return http.build();
    }
}
