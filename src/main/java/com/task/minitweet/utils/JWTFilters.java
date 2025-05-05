package com.task.minitweet.utils;

import com.task.minitweet.domains.models.User;
import com.task.minitweet.services.contract.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTFilters extends OncePerRequestFilter {

    private final JWTTools jwtTools;
    private final UserService userService;

    public JWTFilters(JWTTools jwtTools, UserService userService) {
        this.jwtTools = jwtTools;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");
        String email = null;
        String token = null;

        if(tokenHeader != null && tokenHeader.length()>7 && tokenHeader.startsWith("Bearer ")){
            token = tokenHeader.substring(7);

            try{
                email = jwtTools.getEmailByFrom(token);
            }catch (IllegalArgumentException e){
                System.out.println("Unable to get JWT token");
            }catch (ExpiredJwtException e) {
                System.out.println("JWT token has expired");
            }catch (MalformedJwtException e){
                System.out.println("JWT token is malformed");
            }
        }else {
            System.out.println("Bearer string not foud");
        }

        if(email != null && token !=null && SecurityContextHolder.getContext().getAuthentication() == null){
            User user = userService.findByEmail(email);

            if(user != null){
                Boolean tokenValidity = userService.isTokenValid(user, token);
                if(tokenValidity){
                    UsernamePasswordAuthenticationToken authToken =  new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }else{
                System.out.println("User not found");
            }
        }
        filterChain.doFilter(request, response);
    }
}
