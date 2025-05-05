package com.task.minitweet.configurations;

import com.task.minitweet.domains.models.User;
import com.task.minitweet.services.contract.UserService;
import com.task.minitweet.utils.JWTFilters;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class WenSecurityConfiguration {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JWTFilters jwtFilters;

    public WenSecurityConfiguration(PasswordEncoder passwordEncoder, UserService userService, JWTFilters jwtFilters) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.jwtFilters = jwtFilters;
    }

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity httpSecurity)throws Exception{
        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder.userDetailsService(identifier ->{
            User user = userService.findByIdentifier(identifier);
            if(user ==null){
                throw new UsernameNotFoundException("User not found whit identifier");
            }
            return user;
        })
                .passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.httpBasic(withDefaults()).csrf(csrf-> csrf.disable());

        httpSecurity.authorizeHttpRequests(auth->
                auth.requestMatchers("/api/auth/**").permitAll()
                        .anyRequest().authenticated()
        );
        httpSecurity.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        httpSecurity.exceptionHandling(handling -> handling.authenticationEntryPoint((req, res, ex)->{
            res.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Authentication failed: "+ ex.getMessage()
            );
        }));

        httpSecurity.addFilterBefore(jwtFilters, UsernamePasswordAuthenticationFilter.class);
        return  httpSecurity.build();
    }
}
