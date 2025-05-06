package io.aiven.spring.mysql.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.core.context.SecurityContextHolder;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // config/SecurityConfig.java
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()  // For login/register
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
/*
csrf().disable(): Disables CSRF protection, which is typically unnecessary for stateless REST APIs.

authorizeHttpRequests(...): Sets up which endpoints are public and which are secured.

requestMatchers("/auth/**").permitAll(): Allows unauthenticated access to auth-related endpoints.

.anyRequest().authenticated(): Requires authentication for all other routes.

addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class): Adds your custom JWT filter before the built-in username/password filter so that JWTs are processed first.
 */
/*
/auth/** routes are public.
All other routes need a valid JWT (checked by JwtFilter).
CSRF is disabled (common for APIs).
Your custom JwtFilter is integrated into the security flow.
 */

/*
1. I created a SecurityConfig class to configure Spring Security for the application.
This class is marked with @Configuration and @EnableWebSecurity to tell Spring that this is a custom security setup.

2. Inside this class, I defined a SecurityFilterChain bean.
This is a new way in Spring Security (without extending WebSecurityConfigurerAdapter) to define security rules.

3. I disabled CSRF protection usingsrf().disable().
Since my backend is a REST API and stateless, CSRF isn’t required — especially when using JWT.

4. I used .authorizeHttpRequests() to define public and protected routes.
I allowed open access to /auth/** endpoints, like login and registration, using .permitAll().
All other endpoints are protected using .anyRequest().authenticated().

5. I added my custom JWT filter (JwtFilter) to the security chain.
This filter runs before Spring's default UsernamePasswordAuthenticationFilter.
It checks for the JWT token in requests, validates it, and sets the authentication context.

6. Finally, I built and returned the http security object.
This activates all the above security rules and filter configurations.
 */