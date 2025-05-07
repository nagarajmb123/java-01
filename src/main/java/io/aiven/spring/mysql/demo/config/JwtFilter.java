// config/JwtFilter.java
package io.aiven.spring.mysql.demo.config;

import io.aiven.spring.mysql.demo.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)//This method runs on every HTTP request.
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            String username = jwtUtil.extractUsername(jwt);

            if (username != null && jwtUtil.validateToken(jwt)) {
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(username, null, null);
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request, response);
    }
}
/*
(extends OncePerRequestFilter: Ensures the filter is executed only once per request.)
Creates a UsernamePasswordAuthenticationToken
– This object represents the logged-in user and their credentials.

Sets request details into the authentication object
– This includes extra info like IP address and session, helping with security logging and tracking.

Sets the authenticated user into the SecurityContext
– This tells Spring Security, “Yes, this user is logged in,” so it allows access to protected endpoints.

You can summarize it like this:

"When I send a request with a JWT, the filter checks the token, extracts the user, marks the user as logged in,
 and then allows the request to continue.

 "
UsernamePasswordAuthenticationToken is a Spring Security object that represents a user's login info (like username and roles).

In short:
It’s how Spring knows who the user is and whether they’re authenticated or not.

You can think of it like:

“Hey Spring, this is the logged-in user for this request.”
"


 */