package com.TeaManagement.TeaManagement.service;

import com.TeaManagement.TeaManagement.dao.UserDao;
import com.TeaManagement.TeaManagement.entity.JwtRequest;
import com.TeaManagement.TeaManagement.entity.JwtResponse;
import com.TeaManagement.TeaManagement.entity.User;
import com.TeaManagement.TeaManagement.util.JwtUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;
@Service
public class JwtService implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
        String userName = jwtRequest.getUserName();
        String userPassword = jwtRequest.getUserPassword();

        authenticate(userName,userPassword);

       final UserDetails userDetails = loadUserByUsername(userName);
       String newGeneratedToken = jwtUtil.generateToken(userDetails);
       User user = userDao.findById(userName).get();
       return new JwtResponse(user, newGeneratedToken);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findById(username).get();

        if(user !=null ){
            return new org.springframework.security.core.userdetails.User(
                    user.getEmpNo(),
                    user.getEmpPassword(),
                    getAuthorities(user)

            );
        }else{
            throw new UsernameNotFoundException("Employee number is not valid");

        }

    }

    private Set getAuthorities(User user){
        Set authorities = new HashSet();

        user.getRole().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        });

        return authorities;
    }

    private void authenticate(String userName, String userPassword) throws Exception {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName,userPassword));
        }catch (DisabledException e){
            throw new Exception("User is disabled");
        }catch (BadCredentialsException e){
            throw new Exception("Bad Credentials from the user");
        }

    }


}
