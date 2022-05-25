package com.loft.service.impl;

import com.loft.service.AutoLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AutoLoginServiceImpl implements AutoLoginService {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void autologin(String emailAddress) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(emailAddress);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

        if (token.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(token);
        }
    }
}
