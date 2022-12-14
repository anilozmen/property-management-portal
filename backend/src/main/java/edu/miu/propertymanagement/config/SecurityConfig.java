package edu.miu.propertymanagement.config;

import edu.miu.propertymanagement.filter.JWTFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JWTFilter jwtFilter;

    @Bean

    public UserDetailsService userDetailsSvc() {
        return userDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .cors().and()
                .authorizeRequests()
                .antMatchers("/api/v1/authenticate/**").permitAll()
                .antMatchers("/api/v1/admin/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/properties").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/properties/*").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/properties").hasAuthority("OWNER")
                .antMatchers(HttpMethod.PUT, "/api/v1/properties/*").hasAuthority("OWNER")
                .antMatchers(HttpMethod.PUT, "/api/v1/properties/*/action").hasAuthority("OWNER")
                .antMatchers(HttpMethod.POST, "/api/v1/properties/*/offers").hasAuthority("CUSTOMER")
                .antMatchers(HttpMethod.GET, "/api/v1/properties/*/offers").hasAnyAuthority("OWNER", "CUSTOMER")
                .antMatchers(HttpMethod.PUT, "/api/v1/properties/*/offers/*").hasAnyAuthority("OWNER", "CUSTOMER")
                .antMatchers("/api/v1/offers/").hasAnyAuthority("CUSTOMER", "ADMIN")
                .antMatchers("/api/v1/properties/saved/**").hasAuthority("CUSTOMER")
                .antMatchers(HttpMethod.POST, "/api/v1/messages").hasAuthority("CUSTOMER")
                .antMatchers(HttpMethod.PUT, "/api/v1/messages/**").hasAuthority("OWNER")
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ;

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsSvc());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
}
