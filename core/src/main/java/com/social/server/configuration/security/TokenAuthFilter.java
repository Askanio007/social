package com.social.server.configuration.security;

import com.social.server.dto.UserDto;
import com.social.server.entity.User;
import com.social.server.http.ErrorCode;
import com.social.server.http.Response;
import com.social.server.service.UserService;
import com.social.server.util.TokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class TokenAuthFilter extends AbstractAuthenticationProcessingFilter {

    private final UserService userService;

    public TokenAuthFilter(UserService userService) {
        super("/api/**");
        this.userService = userService;
    }

    @Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        return super.requiresAuthentication(request, response);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        String token = request.getHeader("Authorization");
        if (StringUtils.isBlank(token)) {
            response.setStatus(401);
            response.getWriter().append(Response.error(ErrorCode.TOKEN_NOT_FOUND).toJson());
            return null;
        }
        UserDto userDto = TokenUtil.parseToken(token);
        User user = userService.getById(userDto.getId());

        if (user == null || !user.isEnable()) {
            response.setStatus(401);
            response.getWriter().append(Response.error(ErrorCode.USER_NOT_ACTIVE).toJson());
            return null;
        }

        return new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), Collections.emptyList());
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        chain.doFilter(request, response);
    }

    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }
}
