package com.TeaManagement.TeaManagement.configuration;

import com.TeaManagement.TeaManagement.service.JwtService;
import com.TeaManagement.TeaManagement.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Skip JWT processing for public endpoints
        String path = request.getRequestURI();
        if (path.contains("/authenticate") ||
                path.contains("/registerNewUser") ||
                path.contains("/api/v1/admin/get-all-Departments")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String header = request.getHeader("Authorization");

        String jwtToken = null;
        String userName = null;
        if(header != null && header.startsWith("Bearer ")){
            jwtToken = header.substring(7);

            try{
                userName = jwtUtil.getUserNameFromToken(jwtToken);
            }catch (IllegalArgumentException e){
                System.out.println("Unable to get JWT token");
            }catch (ExpiredJwtException e){
                System.out.println("JWT token is expired");
            }
        } else{
            System.out.println("JWT token does not start with Bearer or is missing.");
        }

        if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = jwtService.loadUserByUsername(userName);

            if(jwtUtil.validateToken(jwtToken, userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails,
                                null,
                                userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }





//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
//                                    FilterChain filterChain) throws ServletException, IOException {
//        final String header = request.getHeader("Authorization");
//
//        String jwtToken = null;
//        String userName = null;
//        if(header != null && header.startsWith("Bearer ")){
//            jwtToken = header.substring(7);
//
//            try{
//                userName = jwtUtil.getUserNameFromToken(jwtToken);
//
//            }catch (IllegalArgumentException e){
//                System.out.println("Unable to get JWT token");
//            }catch (ExpiredJwtException e){
//                System.out.println("JWT token is expired");
//            }
//
//        } else{
//            System.out.println("JWT token does not start with Bearer. ");
//        }
//
//        if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null){
//            UserDetails userDetails = jwtService.loadUserByUsername(userName);
//
//            if(jwtUtil.validateToken(jwtToken, userDetails)){
//                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
//                        new UsernamePasswordAuthenticationToken(userDetails,
//                                null,
//                                userDetails.getAuthorities());
//
//                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//            }
//        }
//        filterChain.doFilter(request,response);
//
//
//
//
//    }

}
