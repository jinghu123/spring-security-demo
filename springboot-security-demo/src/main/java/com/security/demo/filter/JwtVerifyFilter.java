package com.security.demo.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.demo.config.RsaKeyConfig;
import com.security.demo.entity.User;
import com.securitydemo.commons.entiry.Payload;
import com.securitydemo.commons.util.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录核查
 */
public class JwtVerifyFilter extends BasicAuthenticationFilter {

    private RsaKeyConfig rsaKeyConfig;
    public JwtVerifyFilter(AuthenticationManager authenticationManager,RsaKeyConfig rsaKeyConfig) {
        super(authenticationManager);
        this.rsaKeyConfig = rsaKeyConfig;
    }
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer")) {
            chain.doFilter(request, response);
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            PrintWriter writer = response.getWriter();
            Map map = new HashMap();
            map.put("code",HttpServletResponse.SC_FORBIDDEN);
            map.put("msg","请重新登录！！！");
            writer.write(new ObjectMapper().writeValueAsString(map));
            writer.flush();
            writer.close();
        } else {
            String playload = header.replace("Bearer", "");
            Payload<User> token = JwtUtils.getInfoFromToken(playload, rsaKeyConfig.getPublicKey(), User.class);
            User user = token.getUserInfo();
            UsernamePasswordAuthenticationToken authResult = new UsernamePasswordAuthenticationToken(user.getUsername(), null,user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authResult);
            chain.doFilter(request, response);
        }
    }

}
